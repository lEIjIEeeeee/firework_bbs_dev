package com.wlj.firework.core.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.wlj.firework.core.config.WebSecurityConfig;
import com.wlj.firework.core.context.Context;
import com.wlj.firework.core.context.ContextUtils;
import com.wlj.firework.core.modular.auth.constant.AuthConstants;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.modular.common.model.dto.LoginUser;
import com.wlj.firework.core.security.jwt.JwtPayLoad;
import com.wlj.firework.core.security.jwt.JwtTokenUtils;
import com.wlj.firework.core.util.JedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Slf4j
public class CheckTokenFilter extends OncePerRequestFilter {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private JedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        response.setContentType("application/json;CHARSET=UTF-8");
        response.setCharacterEncoding("UTF-8");

        //放行url、资源
        if (checkIsExcludedUrl(request.getRequestURI())
                || request.getRequestURI().contains("/webjars")
                || request.getRequestURI().contains("/swagger-resources")
                || request.getRequestURI().contains("/v2")
                || request.getRequestURI().contains("/druid")) {
            ContextUtils.clear();
            chain.doFilter(request, response);
            return;
        }

        //获取token
        String token = getToken(request);

        //验证token，并返回结果
        JwtPayLoad jwtPayLoad;
        try {
            jwtPayLoad = JwtTokenUtils.getJwtPayLoad(token);
        } catch (Exception e) {
            validatedTokenError(response);
            return;
        }

        //验证通过，设置上下文
        if (jwtPayLoad != null) {
            LoginUser loginUser = redisUtils.getObjWithClass(AuthConstants.SESSION_PREFIX + jwtPayLoad.getUserId(), LoginUser.class);
            if (loginUser == null) {
                validatedTokenExpired(response);
                return;
            }

            if (ObjectUtil.notEqual(token, loginUser.getAuthToken())) {
                validatedTokenError(response);
                return;
            }
            setContextInfo(request, loginUser);
            chain.doFilter(request, response);
        } else {
            validatedTokenError(response);
        }
    }

    private String getToken(HttpServletRequest request) {
        //从header获取token
        String authToken = request.getHeader(AuthConstants.TOKEN_NAME);
        //header中没有就去Cookie中拿
        if (StrUtil.isBlank(authToken)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (AuthConstants.TOKEN_NAME.equals(cookie.getName())) {
                        authToken = cookie.getValue();
                    }
                }
            }
        }
        return authToken;
    }

    private void validatedTokenError(ServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            HttpResult<Void> failure = HttpResult.failure(HttpResultCode.TOKEN_VALIDATE_FAILED);
            out.write(JSON.toJSONString(failure));
            out.flush();
        } catch (IOException e) {
            log.error("token校验异常！");
        }
    }

    private void validatedTokenExpired(ServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            HttpResult<Void> failure = HttpResult.failure(HttpResultCode.TOKEN_EXPIRED);
            out.write(JSON.toJSONString(failure));
            out.flush();
        } catch (IOException e) {
            log.error("token已过期！");
        }
    }

    private void setContextInfo(HttpServletRequest request, LoginUser loginUser) {
        checkLoginUserInfo(loginUser);
        ContextUtils.setContext(Context.builder()
                                       .loginUser(loginUser)
                                       .build());

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private void checkLoginUserInfo(LoginUser loginUser) {
        if (StrUtil.isBlank(loginUser.getId())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "登录用户没有ID！");
        }

        if (StrUtil.isBlank(loginUser.getAccount())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "登录用户没有账号信息！");
        }
    }

    private Boolean checkIsExcludedUrl(String url) {
        return WebSecurityConfig.EXCLUDED_URL_LIST.contains(StrUtil.replace(url, contextPath, StrUtil.EMPTY));
    }

}

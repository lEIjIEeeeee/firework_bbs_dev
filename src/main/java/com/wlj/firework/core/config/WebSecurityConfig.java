package com.wlj.firework.core.config;

import com.wlj.firework.core.filter.CheckTokenFilter;
import com.wlj.firework.core.modular.common.service.GeneralUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wlj
 * @date 2023-05-06
 *
 * @see EnableWebSecurity 使用该注解作用就是导入WebSecurityConfiguration配置类，是核心的过滤器，是请求的认证入口
 * @see EnableGlobalMethodSecurity prePostEnabled设置为true，表示在controller方法上开启注解形式的安全认证机制
 */
@Component
@AllArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private GeneralUserDetailService userDetailService;

    public final static List<String> EXCLUDED_URL_LIST = new ArrayList<>();

    private CheckTokenFilter checkTokenFilter;

    static {
        EXCLUDED_URL_LIST.add("/doc.html");
        EXCLUDED_URL_LIST.add("/authModule/login/register");
        EXCLUDED_URL_LIST.add("/authModule/login/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //不使用session，无需在意csrf攻击，关闭csrf
        http.csrf().disable();
        //跨域相关
        http.cors();
        //登出禁用
        http.logout().disable();
        //基于token，用不到session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //添加无需token验证的请求(antMatchers:允许带通配符的路径匹配；permitAll:允许任何权限通过)
        for (String excludedUrl : EXCLUDED_URL_LIST) {
            http.authorizeRequests()
                .antMatchers(excludedUrl)
                .permitAll();
        }
        //添加所有其他需要token验证的请求(authenticated:需要验证)
        http.authorizeRequests()
            .anyRequest()
            .authenticated();

        http.addFilterBefore(checkTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers()
            .frameOptions()
            .disable()
            .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring()
           .antMatchers("/css/**", "/js/**")
           .and()
           .ignoring()
           .and()
           .ignoring()
           .antMatchers(
                   "/doc.html",
                   "/webjars/**",
                   "/swagger-resources/**",
                   "/v2/api-docs/**");
    }

}

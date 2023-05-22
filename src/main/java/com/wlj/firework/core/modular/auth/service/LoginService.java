package com.wlj.firework.core.modular.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wlj.firework.core.context.HttpContext;
import com.wlj.firework.core.modular.auth.constant.AuthConstants;
import com.wlj.firework.core.modular.auth.dao.*;
import com.wlj.firework.core.modular.auth.enums.RegisterTypeEnum;
import com.wlj.firework.core.modular.auth.manager.MemberManager;
import com.wlj.firework.core.modular.auth.manager.UserManager;
import com.wlj.firework.core.modular.auth.model.entity.*;
import com.wlj.firework.core.modular.auth.model.request.LoginRequest;
import com.wlj.firework.core.modular.auth.model.request.RegisterRequest;
import com.wlj.firework.core.modular.common.constant.NumberConstants;
import com.wlj.firework.core.modular.common.constant.RedisKeyConstants;
import com.wlj.firework.core.modular.common.constant.SystemConstants;
import com.wlj.firework.core.modular.common.enums.EnableOrDisableStatusEnum;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.enums.UserStatusEnum;
import com.wlj.firework.core.modular.common.enums.YesOrNoEnum;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.common.model.dto.LoginUser;
import com.wlj.firework.core.security.jwt.JwtPayLoad;
import com.wlj.firework.core.security.jwt.JwtTokenUtils;
import com.wlj.firework.core.util.JavaBeanUtils;
import com.wlj.firework.core.util.JedisUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class LoginService {

    private final UserMapper userMapper;
    private final MemberMapper memberMapper;
    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final RoleMenuRelMapper roleMenuRelMapper;

    private final UserManager userManager;
    private final MemberManager memberManager;

    private final RedissonClient redissonClient;

    private final JedisUtils redisUtils;

    /**
     * 1.添加redis锁，保证线程安全
     * 2.短信验证码校验（暂无）
     * 3.校验账号唯一性，生成密码盐
     * 4.密码加密
     * 5.存储信息到sys_user、tbl_member
     * 6.假设有账户金额等其他业务，存储用户信息后进行相关信息的初始化操作（暂无）
     * 假如是前台通过微信直接注册登录增加后续流程：
     * 7.生成token，存储LoginUser信息到redis，存储token到Cookie
     * finally：
     * 8.解锁
     */
    @Transactional(rollbackFor = Exception.class)
    public LoginUser register(RegisterRequest request) {
        String phone = request.getPhone();
        RLock rLock = redissonClient.getLock(RedisKeyConstants.REGISTER_PREFIX + phone);
        try {
            if (!rLock.tryLock(NumberConstants.INTEGER_TEN, TimeUnit.SECONDS)) {
                log.error("用户注册获取锁超时 phone:{}", phone);
                throw new BizException(HttpResultCode.BIZ_EXCEPTION, "phone:" + phone + "用户正在注册");
            }
            Integer count = userMapper.selectCount(Wrappers.lambdaQuery(User.class)
                                                           .eq(User::getPhone, phone));
            if (count > 0) {
                throw new BizException(HttpResultCode.BIZ_EXCEPTION, "该手机号已被注册！");
            }
            return saveUserInfo(request);
        } catch (Exception e) {
            log.error("用户注册流程异常！", e);
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, e.getMessage());
        } finally {
            if (rLock != null && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }
    }

    private LoginUser saveUserInfo(RegisterRequest request) {
        String phone = request.getPhone();
        if (StrUtil.isBlank(request.getPassword())) {
            request.setPassword(RandomUtil.randomString(6));
        }
        String salt = RandomUtil.randomString(4);
        String nickName = DesensitizedUtil.mobilePhone(phone) + "用户";

        User user = User.builder()
                        .phone(phone)
                        .account(phone)
                        .password(encryptPwd(request.getPassword(), salt))
                        .salt(salt)
                        .name(nickName)
                        .status(UserStatusEnum.NORMAL.getCode())
                        .createTime(DateUtil.date())
                        .build();
        userMapper.insert(user);

        Member member = Member.builder()
                              .id(user.getId())
                              .account(phone)
                              .nickName(nickName)
                              .phone(phone)
                              .registerType(RegisterTypeEnum.NORMAL.getCode())
                              .status(UserStatusEnum.NORMAL.getCode())
                              .createId(user.getId())
                              .createTime(DateUtil.date())
                              .build();
        memberMapper.insert(member);

        return JavaBeanUtils.map(member, LoginUser.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public LoginUser login(LoginRequest request) {
        User user = userManager.getUserByAccount(request.getAccount());
        if (ObjectUtil.isNull(user)) {
            throw new BizException(HttpResultCode.USER_ACCOUNT_NOT_EXIST);
        }

        checkUserStatus(user);

        //TODO 假如有验证码则进行验证码校验
        String encryptPwd = encryptPwd(request.getPassword(), user.getSalt());
        if (ObjectUtil.notEqual(encryptPwd, user.getPassword())) {
            throw new BizException(HttpResultCode.USER_PASSWORD_VALIDATE_FAILED);
        }

        updateLastLoginTime(user.getId());

        LoginUser loginUser = JavaBeanUtils.map(user, LoginUser.class);

        fillLoginUserInfo(loginUser);
        return loginUser;
    }

    private void checkUserStatus(User user) {
        Integer userStatus = user.getStatus();
        if (userStatus == null) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION);
        }
        if (UserStatusEnum.FREEZE.getCode().equals(user.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "该用户已被冻结！");
        }
        if (UserStatusEnum.DELETE.getCode().equals(user.getStatus())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "该用户已被删除");
        }
    }

    private void updateLastLoginTime(String userId) {
        memberMapper.update(null, Wrappers.lambdaUpdate(Member.class)
                                          .set(Member::getLastLoginTime, DateUtil.date())
                                          .eq(Member::getId, userId));
    }

    private String encryptPwd(String password, String salt) {
        if (StrUtil.isBlank(password)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "密码不能为空");
        }
        if (StrUtil.isBlank(salt)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "盐值不能为空");
        }
        return SecureUtil.md5(password + SystemConstants.APP_ID + salt);
    }

    private void fillLoginUserInfo(LoginUser loginUser) {
        Member member = memberManager.getMemberByIdWithException(loginUser.getId());
        loginUser.setNickName(member.getNickName());
        if (StrUtil.isNotBlank(member.getRealName())) {
            loginUser.setRealName(member.getRealName());
        }

        //根据roleId填充角色集合
        fillLoginUserRoles(loginUser);

        //填充功能权限信息
        fillPermissions(loginUser);

        String token;
        LoginUser redisUser = redisUtils.getObjWithClass(AuthConstants.SESSION_PREFIX + loginUser.getId(), LoginUser.class);
        if (ObjectUtil.isNotNull(redisUser)) {
            token = redisUser.getAuthToken();
            if (JwtTokenUtils.isTokenExpired(token)) {
                token = generateUserToken(loginUser);
            }
        } else {
            token = generateUserToken(loginUser);
        }
        loginUser.setAuthToken(token);

        //存储loginUser信息到redis，时限默认一年
        setUserSession(loginUser);

        //保存token到Cookie
        saveLoginTokenToCookie(token);
    }

    private void fillLoginUserRoles(LoginUser loginUser) {
        if (StrUtil.isBlank(loginUser.getRoleId())) {
            return;
        }
        List<String> roleIds = CollUtil.newArrayList(StrUtil.split(loginUser.getRoleId(), StrUtil.COMMA));
        if (CollUtil.isNotEmpty(roleIds)) {
            loginUser.setRoleIds(roleIds);
        }
        List<Role> roleList = roleMapper.selectList(Wrappers.lambdaQuery(Role.class)
                                                            .in(Role::getId, roleIds));
        if (CollUtil.isEmpty(roleList)) {
            return;
        }
        loginUser.setRoleCodes(roleList.stream()
                                       .map(Role::getCode)
                                       .collect(Collectors.toSet()));
    }

    private void fillPermissions(LoginUser loginUser) {
        if (CollUtil.isEmpty(loginUser.getRoleIds())) {
            return;
        }
        List<RoleMenuRel> roleMenuRelList = roleMenuRelMapper.selectList(Wrappers.lambdaQuery(RoleMenuRel.class)
                                                                                 .in(RoleMenuRel::getRoleId, loginUser.getRoleIds()));
        if (CollUtil.isEmpty(roleMenuRelList)) {
            return;
        }
        Set<String> menuIds = roleMenuRelList.stream()
                                             .map(RoleMenuRel::getMenuId)
                                             .collect(Collectors.toSet());
        List<Menu> menuList = menuMapper.selectList(Wrappers.lambdaQuery(Menu.class)
                                                            .in(Menu::getId, menuIds)
                                                            .eq(Menu::getStatus, EnableOrDisableStatusEnum.ENABLE.getCode())
                                                            .eq(Menu::getMenuFlag, YesOrNoEnum.Y.getCode()));
        if (CollUtil.isEmpty(menuList)) {
            return;
        }
        loginUser.setPermissions(menuList.stream()
                                         .map(Menu::getCode)
                                         .collect(Collectors.toList()));
    }

    private void setUserSession(LoginUser loginUser) {
        redisUtils.setObjWithDay(AuthConstants.SESSION_PREFIX + loginUser.getId(), loginUser, 365);
    }

    private void saveLoginTokenToCookie(String token) {
        HttpServletResponse response = HttpContext.getResponse();
        if (response != null) {
            Cookie cookie = new Cookie(AuthConstants.TOKEN_NAME, token);
            cookie.setMaxAge(Math.toIntExact(JwtTokenUtils.getExpireSeconds()));
            cookie.setHttpOnly(true);
            cookie.setPath(StrUtil.SLASH);
            response.addCookie(cookie);
        }
    }

    private String generateUserToken(LoginUser loginUser) {
        return JwtTokenUtils.generateToken(JwtPayLoad.builder()
                                                     .userId(loginUser.getId())
                                                     .account(loginUser.getAccount())
                                                     .build());
    }

}

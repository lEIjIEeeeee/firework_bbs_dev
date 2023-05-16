package com.wlj.firework.core.modular.auth.controller;

import com.wlj.firework.core.modular.auth.model.request.LoginRequest;
import com.wlj.firework.core.modular.auth.model.request.RegisterRequest;
import com.wlj.firework.core.modular.auth.service.LoginService;
import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.modular.common.model.dto.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "权限模块-登录接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/authModule/login")
public class LoginController {

    private final LoginService loginService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public HttpResult<LoginUser> register(@RequestBody @Validated RegisterRequest request) {
        return HttpResult.success(loginService.register(request));
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public HttpResult<LoginUser> login(@RequestBody @Validated LoginRequest request) {
        return HttpResult.success(loginService.login(request));
    }
}

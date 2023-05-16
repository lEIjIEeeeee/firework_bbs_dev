package com.wlj.firework.core.modular.user.controller;

import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.modular.user.request.PersonInfoEditRequest;
import com.wlj.firework.core.modular.user.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户模块-个人中心接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/userModule/person")
public class PersonController {

    private final PersonService personService;

    @ApiOperation(value = "修改个人信息")
    @PostMapping("/personInfoEdit")
    public HttpResult<Void> personInfoEdit(@RequestBody @Validated PersonInfoEditRequest request) {
        personService.editPersonInfo(request);
        return HttpResult.success();
    }

}

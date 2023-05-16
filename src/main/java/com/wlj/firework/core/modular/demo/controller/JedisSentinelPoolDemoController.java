package com.wlj.firework.core.modular.demo.controller;

import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.modular.demo.model.request.SetKeyRequest;
import com.wlj.firework.core.modular.demo.service.JedisSentinelPoolDemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "测试模块-jedis连接测试接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/demoModule/sentinel")
public class JedisSentinelPoolDemoController {

    private final JedisSentinelPoolDemoService jedisDemoService;

    @ApiOperation(value = "存储数据")
    @PostMapping("/setKey")
    public HttpResult<Void> setKey(@RequestBody @Validated SetKeyRequest setKeyRequest) {
        jedisDemoService.setKey(setKeyRequest);
        return HttpResult.success();
    }

    @ApiOperation(value = "读取数据")
    @GetMapping("/getValue")
    public String getValue(@NotBlank String key) {
        return jedisDemoService.getValue(key);
    }

}

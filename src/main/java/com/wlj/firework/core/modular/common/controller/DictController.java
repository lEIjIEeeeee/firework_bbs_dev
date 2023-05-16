package com.wlj.firework.core.modular.common.controller;

import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.modular.common.model.request.DictRequest;
import com.wlj.firework.core.modular.common.model.request.DictTypeRequest;
import com.wlj.firework.core.modular.common.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "公共模块-字典接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/commonModule/dict")
public class DictController {

    private final DictService dictService;

    @ApiOperation(value = "新增字典类型")
    @PostMapping("/addDictType")
    public HttpResult<Void> addDictType(@RequestBody @Validated DictTypeRequest request) {
        dictService.addDictType(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "新增字典值")
    @PostMapping("/addDict")
    public HttpResult<Void> addDict(@RequestBody @Validated DictRequest request) {
        dictService.addDict(request);
        return HttpResult.success();
    }

}

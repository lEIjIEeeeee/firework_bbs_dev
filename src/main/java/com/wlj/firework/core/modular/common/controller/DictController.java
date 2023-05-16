package com.wlj.firework.core.modular.common.controller;

import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.modular.common.model.PageVO;
import com.wlj.firework.core.modular.common.model.converter.CommonConverter;
import com.wlj.firework.core.modular.common.model.request.*;
import com.wlj.firework.core.modular.common.model.response.DictResponse;
import com.wlj.firework.core.modular.common.model.response.DictTypeResponse;
import com.wlj.firework.core.modular.common.service.DictService;
import com.wlj.firework.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "修改字典类型")
    @PostMapping("/editDictType")
    public HttpResult<Void> editDictType(@RequestBody @Validated(DictTypeRequest.Edit.class)
                                                 DictTypeRequest request) {
        dictService.editDictType(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除字典类型")
    @PostMapping("/deleteDictType")
    public HttpResult<Void> deleteDictType(@RequestBody @Validated DictTypeDeleteRequest request) {
        dictService.deleteDictType(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "查询字典类型详情")
    @GetMapping("/getDictTypeDetail")
    public HttpResult<DictTypeResponse> getDictTypeDetail(@RequestParam String id) {
        return HttpResult.success(JavaBeanUtils.map(dictService.getDictTypeDetail(id), DictTypeResponse.class));
    }

    @ApiOperation(value = "分页查询字典类型列表")
    @GetMapping("/listPageDictType")
    public HttpResult<PageVO<DictTypeResponse>> listPageDictType(@Validated(BaseQueryRequest.ListPage.class)
                                                                         DictTypeQueryRequest request) {
        return HttpResult.success(CommonConverter.convertPage(dictService.listPageDictType(request), DictTypeResponse.class));
    }

    @ApiOperation(value = "新增字典值")
    @PostMapping("/addDict")
    public HttpResult<Void> addDict(@RequestBody @Validated DictRequest request) {
        dictService.addDict(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改字典值")
    @PostMapping("/editDict")
    public HttpResult<Void> editDict(@RequestBody @Validated(DictRequest.Edit.class) DictRequest request) {
        dictService.editDict(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除字典值")
    @PostMapping("/deleteDict")
    public HttpResult<Void> deleteDict(@RequestBody @Validated DeleteDictRequest request) {
        dictService.deleteDict(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "查询字典值详情")
    @GetMapping("/getDictDetail")
    public HttpResult<DictResponse> getDictDetail(@RequestParam String id) {
        return HttpResult.success(JavaBeanUtils.map(dictService.getDictDetail(id), DictResponse.class));
    }

    @ApiOperation(value = "分页查询字典值列表")
    @GetMapping("/listPageDict")
    public HttpResult<PageVO<DictResponse>> listPageDict(@Validated(BaseQueryRequest.ListPage.class)
                                                                 DictQueryRequest request) {
        return HttpResult.success(CommonConverter.convertPage(dictService.listPageDict(request), DictResponse.class));
    }

}

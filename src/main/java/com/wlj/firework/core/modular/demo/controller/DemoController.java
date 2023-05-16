package com.wlj.firework.core.modular.demo.controller;

import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.modular.common.model.PageVO;
import com.wlj.firework.core.modular.common.model.converter.CommonConverter;
import com.wlj.firework.core.modular.common.model.request.BaseQueryRequest;
import com.wlj.firework.core.modular.demo.model.request.DemoDeleteRequest;
import com.wlj.firework.core.modular.demo.model.request.DemoRequest;
import com.wlj.firework.core.modular.demo.model.response.DemoResponse;
import com.wlj.firework.core.modular.demo.service.DemoService;
import com.wlj.firework.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "测试模块-测试接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/demoModule/demo")
public class DemoController {

    private final DemoService demoService;

    @ApiOperation(value = "添加测试数据")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated DemoRequest request) {
        demoService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改测试数据")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated(DemoRequest.Edit.class)
                                         DemoRequest request) {
        demoService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除测试数据")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated DemoDeleteRequest request) {
        demoService.delete(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "查询信息详情测试接口")
    @GetMapping("/get")
    public HttpResult<DemoResponse> get(@RequestParam String id) {
        return HttpResult.success(JavaBeanUtils.map(demoService.get(id), DemoResponse.class));
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/listPage")
    public HttpResult<PageVO<DemoResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                             BaseQueryRequest request) {
        return HttpResult.success(CommonConverter.convertPage(demoService.listPage(request), DemoResponse.class));
    }

}

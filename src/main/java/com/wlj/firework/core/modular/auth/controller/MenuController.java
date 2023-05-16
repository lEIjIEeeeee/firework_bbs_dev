package com.wlj.firework.core.modular.auth.controller;

import cn.hutool.core.lang.tree.Tree;
import com.wlj.firework.core.modular.auth.model.request.MenuDeleteRequest;
import com.wlj.firework.core.modular.auth.model.request.MenuQueryRequest;
import com.wlj.firework.core.modular.auth.model.request.MenuRequest;
import com.wlj.firework.core.modular.auth.model.response.MenuDetailResponse;
import com.wlj.firework.core.modular.auth.service.MenuService;
import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "权限模块-菜单接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/authModule/menu")
public class MenuController {

    private final MenuService menuService;

    @ApiOperation(value = "添加菜单")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated MenuRequest request) {
        menuService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "编辑菜单")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated MenuRequest request) {
        menuService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除菜单")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated MenuDeleteRequest request) {
        menuService.delete(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "获取菜单树")
    @GetMapping("/getMenuTree")
    public HttpResult<Tree<String>> getMenuTree(@Validated MenuQueryRequest request) {
        return HttpResult.success(menuService.getMenuTree(request));
    }

    @ApiOperation(value = "查询菜单详情")
    @GetMapping("/get")
    public HttpResult<MenuDetailResponse> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(JavaBeanUtils.map(menuService.get(id), MenuDetailResponse.class));
    }
}

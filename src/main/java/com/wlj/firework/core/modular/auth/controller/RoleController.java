package com.wlj.firework.core.modular.auth.controller;

import com.wlj.firework.core.modular.common.model.HttpResult;
import com.wlj.firework.core.modular.common.model.PageVO;
import com.wlj.firework.core.modular.common.model.converter.CommonConverter;
import com.wlj.firework.core.modular.common.model.request.BaseDeleteRequest;
import com.wlj.firework.core.modular.common.model.request.BaseQueryRequest;
import com.wlj.firework.core.modular.auth.model.request.PermissionSettingsRequest;
import com.wlj.firework.core.modular.auth.model.request.RoleRequest;
import com.wlj.firework.core.modular.auth.model.response.RoleDetailResponse;
import com.wlj.firework.core.modular.auth.model.response.RoleListResponse;
import com.wlj.firework.core.modular.auth.service.RoleService;
import com.wlj.firework.core.util.JavaBeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "权限模块-角色接口")
@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/authModule/role")
public class RoleController {

    private final RoleService roleService;

    @ApiOperation(value = "添加新角色")
    @PostMapping("/add")
    public HttpResult<Void> add(@RequestBody @Validated RoleRequest request) {
        roleService.add(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "修改角色信息")
    @PostMapping("/edit")
    public HttpResult<Void> edit(@RequestBody @Validated RoleRequest request) {
        roleService.edit(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/delete")
    public HttpResult<Void> delete(@RequestBody @Validated BaseDeleteRequest request) {
        roleService.delete(request);
        return HttpResult.success();
    }

    @ApiOperation(value = "查询角色详情")
    @GetMapping("/get")
    public HttpResult<RoleDetailResponse> get(@RequestParam @NotBlank String id) {
        return HttpResult.success(JavaBeanUtils.map(roleService.get(id), RoleDetailResponse.class));
    }

    @ApiOperation(value = "分页查询角色信息列表")
    @GetMapping("/listPage")
    public HttpResult<PageVO<RoleListResponse>> listPage(@Validated(BaseQueryRequest.ListPage.class)
                                                                 BaseQueryRequest request) {
        return HttpResult.success(CommonConverter.convertPage(roleService.listPage(request), RoleListResponse.class));
    }

    @ApiOperation(value = "设置角色权限")
    @PostMapping("/permissionSettings")
    public HttpResult<Void> permissionSettings(@RequestBody @Validated PermissionSettingsRequest request) {
        roleService.permissionSettings(request);
        return HttpResult.success();
    }

}

package com.wlj.firework.core.modular.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlj.firework.core.context.ContextUtils;
import com.wlj.firework.core.modular.auth.dao.MenuMapper;
import com.wlj.firework.core.modular.auth.dao.RoleMenuRelMapper;
import com.wlj.firework.core.modular.auth.model.entity.Menu;
import com.wlj.firework.core.modular.auth.model.entity.RoleMenuRel;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.common.model.request.BaseDeleteRequest;
import com.wlj.firework.core.modular.common.model.request.BaseQueryRequest;
import com.wlj.firework.core.modular.auth.dao.RoleMapper;
import com.wlj.firework.core.modular.auth.manager.RoleManager;
import com.wlj.firework.core.modular.auth.model.entity.Role;
import com.wlj.firework.core.modular.auth.model.request.PermissionSettingsRequest;
import com.wlj.firework.core.modular.auth.model.request.RoleRequest;
import com.wlj.firework.core.modular.common.util.JavaBeanUtils;
import com.wlj.firework.core.modular.common.util.PageUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RoleService {

    private final RoleMapper roleMapper;
    private final MenuMapper menuMapper;
    private final RoleMenuRelMapper roleMenuRelMapper;

    private final RoleManager roleManager;

    @Transactional(rollbackFor = Exception.class)
    public void add(RoleRequest request) {
        Role roleByCode = roleManager.getByCode(request.getCode());
        if (ObjectUtil.isNotNull(roleByCode)) {
            throw new BizException(HttpResultCode.DATA_EXISTED);
        }
        Role role = JavaBeanUtils.map(request, Role.class);
        roleMapper.insert(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(RoleRequest request) {
        Role role = roleManager.getByCode(request.getCode());
        if (ObjectUtil.isNull(role)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTS);
        }
        if (ObjectUtil.notEqual(request.getId(), role.getId())) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION);
        }
        JavaBeanUtils.map(request, role);
        roleMapper.updateById(role);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(BaseDeleteRequest request) {
        Role role = roleManager.getByIdWithException(request.getId());
        roleMapper.deleteById(role.getId());
    }

    public Role get(String id) {
        return roleManager.getByIdWithException(id);
    }

    public Page<Role> listPage(BaseQueryRequest request) {
        return roleMapper.selectPage(PageUtils.createPage(request), Wrappers.lambdaQuery(Role.class)
                                                                            .orderByAsc(Role::getSort)
                                                                            .orderByDesc(Role::getUpdateTime));
    }

    @Transactional(rollbackFor = Exception.class)
    public void permissionSettings(PermissionSettingsRequest request) {
        String userId = ContextUtils.getCurrentUserId();
        roleManager.getByIdWithException(request.getRoleId());
        if (CollUtil.isEmpty(request.getMenuIds())) {
            return;
        }
        List<Menu> menuList = menuMapper.selectList(Wrappers.lambdaQuery(Menu.class)
                                                            .in(Menu::getId, request.getMenuIds()));
        if (CollUtil.isEmpty(menuList)) {
            throw new BizException(HttpResultCode.BIZ_EXCEPTION, "所选菜单不存在！");
        }

        List<RoleMenuRel> roleMenuRelList = roleMenuRelMapper.selectList(Wrappers.lambdaQuery(RoleMenuRel.class)
                                                                                 .eq(RoleMenuRel::getRoleId, request.getRoleId()));
        if (CollUtil.isNotEmpty(roleMenuRelList)) {
            Set<String> roleMenuRelIds = roleMenuRelList.stream()
                                                        .map(RoleMenuRel::getId)
                                                        .collect(Collectors.toSet());
            roleMenuRelMapper.deleteBatchIds(roleMenuRelIds);
        }
        List<RoleMenuRel> roleMenuRelListNew = CollUtil.newArrayList();
        for (String menuId : request.getMenuIds()) {
            RoleMenuRel roleMenuRel = RoleMenuRel.builder()
                                                 .roleId(request.getRoleId())
                                                 .menuId(menuId)
                                                 .createId(userId)
                                                 .createTime(DateUtil.date())
                                                 .build();
            roleMenuRelListNew.add(roleMenuRel);
        }
        roleMenuRelMapper.insertBatchSomeColumn(roleMenuRelListNew);
    }

}

package com.wlj.firework.core.modular.auth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wlj.firework.core.context.ContextUtils;
import com.wlj.firework.core.modular.auth.dao.MenuMapper;
import com.wlj.firework.core.modular.auth.manager.MenuManager;
import com.wlj.firework.core.modular.auth.model.entity.Menu;
import com.wlj.firework.core.modular.auth.model.request.MenuDeleteRequest;
import com.wlj.firework.core.modular.auth.model.request.MenuQueryRequest;
import com.wlj.firework.core.modular.auth.model.request.MenuRequest;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.common.util.JavaBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MenuService {

    private final MenuMapper menuMapper;

    private final MenuManager menuManager;

    @Transactional(rollbackFor = Exception.class)
    public void add(MenuRequest request) {
        Menu menuByCode = menuManager.getMenuByCode(request.getCode());
        if (ObjectUtil.isNotNull(menuByCode)) {
            throw new BizException(HttpResultCode.DATA_EXISTED);
        }
        Menu menu = JavaBeanUtils.map(request, Menu.class);
        menu.setMenuFlag(request.getMenuFlag().getCode())
            .setStatus(request.getStatus().getCode())
            .setCreateId(ContextUtils.getCurrentUserId())
            .setCreateTime(DateUtil.date());
        fillParentInfo(menu);
        menuMapper.insert(menu);
    }

    @Transactional(rollbackFor = Exception.class)
    public void edit(MenuRequest request) {
        Menu menu = menuMapper.selectById(request.getId());
        JavaBeanUtils.map(request, menu);
        menu.setMenuFlag(request.getMenuFlag().getCode())
            .setStatus(request.getStatus().getCode())
            .setUpdateId(ContextUtils.getCurrentUserId())
            .setUpdateTime(DateUtil.date());
        fillParentInfo(menu);
        menuMapper.updateById(menu);
    }

    private void fillParentInfo(Menu menu) {
        if (Menu.TREE_ROOT_ID.equals(menu.getPid())) {
            menu.setPids(StrUtil.BRACKET_START + Menu.TREE_ROOT_ID + StrUtil.BRACKET_END);
            menu.setLevels(1);
        } else {
            Menu pMenu = menuManager.getByIdWithException(menu.getPid());
            menu.setPids(pMenu.getPids() + StrUtil.COMMA + StrUtil.BRACKET_START + pMenu.getId() + StrUtil.BRACKET_END);
            menu.setLevels(pMenu.getLevels() + 1);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(MenuDeleteRequest request) {
        Menu menu = menuManager.getByIdWithException(request.getId());
        menuMapper.deleteById(menu.getId());
    }

    public Tree<String> getMenuTree(MenuQueryRequest request) {
        List<Menu> menuList = getMenuList(request);
        if (CollUtil.isEmpty(menuList)) {
            return Menu.buildTreeRoot();
        }

        List<Tree<String>> treeList = buildMenuTreeList(menuList);
        if (CollUtil.isEmpty(treeList)) {
            menuList.forEach(menu -> menu.setPid(Menu.TREE_ROOT_ID));
            treeList = buildMenuTreeList(menuList);
        }

        return Menu.buildTreeRoot(treeList);
    }

    private List<Tree<String>> buildMenuTreeList(List<Menu> menuList) {
        return TreeUtil.build(menuList, Menu.TREE_ROOT_ID, Menu.getTreeNodeConfig(),
                (menu, treeNode) -> {
                    treeNode.setId(menu.getId());
                    treeNode.setParentId(menu.getPid());
                    treeNode.setName(menu.getName());
                    treeNode.setWeight(menu.getSort());
                    treeNode.putExtra(Menu.CODE, menu.getCode());
                    treeNode.putExtra(Menu.URL, menu.getUrl());
                    treeNode.setChildren(null);
                });
    }

    private List<Menu> getMenuList(MenuQueryRequest request) {
        return menuMapper.selectList(Wrappers.lambdaQuery(Menu.class)
                                             .and(StrUtil.isNotBlank(request.getKeywords()),
                                                     wrapper -> wrapper.like(Menu::getCode, request.getKeywords())
                                                                       .or()
                                                                       .like(Menu::getName, request.getKeywords()))
                                             .eq(request.getLevels() != null, Menu::getLevels, request.getLevels())
                                             .eq(request.getMenuFlag() != null, Menu::getMenuFlag, request.getMenuFlag() != null ? request.getMenuFlag().getCode() : null)
                                             .eq(request.getStatus() != null, Menu::getStatus, request.getStatus() != null ? request.getStatus().getCode() : null)
                                             .orderByAsc(Menu::getSort)
                                             .orderByDesc(Menu::getUpdateTime));
    }

    public Menu get(String id) {
        return menuManager.getByIdWithException(id);
    }

}

package com.wlj.firework.core.modular.auth.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wlj.firework.core.modular.auth.dao.MenuMapper;
import com.wlj.firework.core.modular.auth.model.entity.Menu;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MenuManager {

    private final MenuMapper menuMapper;

    public Menu getMenuById(String id) {
        return menuMapper.selectById(id);
    }

    public Menu getMenuByIdWithException(String id) {
        Menu menu = menuMapper.selectById(id);
        if (ObjectUtil.isNull(menu)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTS);
        }
        return menu;
    }

    public Menu getMenuByCode(String code) {
        return menuMapper.selectOne(Wrappers.lambdaQuery(Menu.class)
                                            .eq(Menu::getCode, code));
    }

}

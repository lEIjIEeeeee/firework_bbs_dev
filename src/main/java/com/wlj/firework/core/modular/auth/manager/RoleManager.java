package com.wlj.firework.core.modular.auth.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.auth.dao.RoleMapper;
import com.wlj.firework.core.modular.auth.model.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleManager {

    private final RoleMapper roleMapper;

    public Role getById(String id) {
        return roleMapper.selectById(id);
    }

    public Role getByIdWithException(String id) {
        Role role = getById(id);
        if (ObjectUtil.isNull(role)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTS, "角色信息不存在");
        }
        return role;
    }

    public Role getByCode(String code) {
        return roleMapper.selectOne(Wrappers.lambdaQuery(Role.class)
                                            .eq(Role::getCode, code));

    }

}

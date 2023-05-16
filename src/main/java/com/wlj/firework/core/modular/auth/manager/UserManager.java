package com.wlj.firework.core.modular.auth.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wlj.firework.core.modular.auth.dao.UserMapper;
import com.wlj.firework.core.modular.auth.model.entity.User;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserManager {

    private final UserMapper userMapper;

    public User getUserById(String id) {
        return userMapper.selectById(id);
    }

    public User getUserByIdWithException(String id) {
        User user = userMapper.selectById(id);
        if (ObjectUtil.isNull(user)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTS);
        }
        return user;
    }

    public User getUserByPhone(String phone) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                                            .eq(User::getPhone, phone));
    }

    public User getUserByAccount(String account) {
        return userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                                            .eq(User::getAccount, account));
    }
}

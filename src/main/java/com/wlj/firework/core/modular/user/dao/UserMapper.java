package com.wlj.firework.core.modular.user.dao;

import com.wlj.firework.core.modular.user.model.entity.User;
import com.wlj.firework.core.modular.common.dao.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends MyBaseMapper<User> {
}
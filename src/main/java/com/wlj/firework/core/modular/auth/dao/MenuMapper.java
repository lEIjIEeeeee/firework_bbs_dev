package com.wlj.firework.core.modular.auth.dao;

import com.wlj.firework.core.modular.auth.model.entity.Menu;
import com.wlj.firework.core.modular.common.dao.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper extends MyBaseMapper<Menu> {
}
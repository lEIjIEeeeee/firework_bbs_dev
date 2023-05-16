package com.wlj.firework.core.modular.demo.dao;

import com.wlj.firework.core.modular.common.dao.MyBaseMapper;
import com.wlj.firework.core.modular.demo.model.entity.Demo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DemoMapper extends MyBaseMapper<Demo> {
}
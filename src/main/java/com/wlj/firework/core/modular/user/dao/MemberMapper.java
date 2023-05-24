package com.wlj.firework.core.modular.user.dao;

import com.wlj.firework.core.modular.user.model.entity.Member;
import com.wlj.firework.core.modular.common.dao.MyBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper extends MyBaseMapper<Member> {
}
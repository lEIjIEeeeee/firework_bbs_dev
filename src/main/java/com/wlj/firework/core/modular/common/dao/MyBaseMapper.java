package com.wlj.firework.core.modular.common.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

public interface MyBaseMapper<T> extends BaseMapper<T> {

    int deleteByIdWithFill(T param);

    int deleteBatchWithFill(@Param(Constants.ENTITY) T param, @Param(Constants.WRAPPER) Wrapper<T> wrappers);

    Integer insertBatchSomeColumn(Collection<T> entityList);

}

package com.wlj.firework.core.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.wlj.firework.core.context.ContextUtils;
import com.wlj.firework.core.modular.common.constant.SystemConstants;
import com.wlj.firework.core.modular.common.enums.YesOrNoEnum;
import com.wlj.firework.core.modular.common.model.dto.LoginUser;
import com.wlj.firework.core.modular.common.model.entity.BaseDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class FillBaseFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Object o = metaObject.getOriginalObject();
        if (o instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) o;
            if (ObjectUtil.notEqual(SystemConstants.SYSTEM_ID, baseDO.getCreateId())) {
                LoginUser currentUser = ContextUtils.getCurrentUser();
                if (StrUtil.isBlank(baseDO.getCreateId())) {
                    baseDO.setCreateId(currentUser.getId());
                }
                if (StrUtil.isBlank(baseDO.getUpdateId())) {
                    baseDO.setUpdateId(currentUser.getId());
                }
            }
            if (ObjectUtil.isNull(baseDO.getCreateTime())) {
                baseDO.setCreateTime(DateUtil.date());
            }
            if (ObjectUtil.isNull(baseDO.getUpdateTime())) {
                baseDO.setUpdateTime(DateUtil.date());
            }
            if (ObjectUtil.isNull(baseDO.getIsDel())) {
                baseDO.setIsDel(YesOrNoEnum.N.getCode());
            }
        } else {
            strictInsertFill(metaObject, BaseDO.CREATE_TIME, Date.class, DateUtil.date());
            strictInsertFill(metaObject, BaseDO.UPDATE_TIME, Date.class, DateUtil.date());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object o = metaObject.getOriginalObject();
        if (o instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) o;
            if (ObjectUtil.notEqual(SystemConstants.SYSTEM_ID, baseDO.getUpdateId())) {
                LoginUser currentUser = ContextUtils.getCurrentUser();
                if (StrUtil.isBlank(baseDO.getUpdateId())) {
                    baseDO.setUpdateId(currentUser.getId());
                }
            }
            if (ObjectUtil.isNull(baseDO.getUpdateTime())) {
                baseDO.setUpdateTime(DateUtil.date());
            }
        } else {
            strictUpdateFill(metaObject, BaseDO.UPDATE_TIME, Date.class, DateUtil.date());
        }
    }

}

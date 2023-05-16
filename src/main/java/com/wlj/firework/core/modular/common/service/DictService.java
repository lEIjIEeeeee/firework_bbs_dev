package com.wlj.firework.core.modular.common.service;

import cn.hutool.core.util.ObjectUtil;
import com.wlj.firework.core.modular.common.dao.DictMapper;
import com.wlj.firework.core.modular.common.dao.DictTypeMapper;
import com.wlj.firework.core.modular.common.enums.DictTypeBizOrSystemFlagEnum;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.common.manager.DictManager;
import com.wlj.firework.core.modular.common.model.entity.Dict;
import com.wlj.firework.core.modular.common.model.entity.DictType;
import com.wlj.firework.core.modular.common.model.request.DictRequest;
import com.wlj.firework.core.modular.common.model.request.DictTypeRequest;
import com.wlj.firework.core.util.JavaBeanUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class DictService {

    private final DictTypeMapper dictTypeMapper;
    private final DictMapper dictMapper;

    private final DictManager dictManager;

    @Transactional(rollbackFor = Exception.class)
    public void addDictType(DictTypeRequest request) {
        DictType dictType = dictManager.getDictTypeByCode(request.getCode());
        if (ObjectUtil.isNotNull(dictType)) {
            throw new BizException(HttpResultCode.DATA_EXISTED);
        }
        DictType dictTypeNew = JavaBeanUtils.map(request, DictType.class);
        dictTypeNew.setType(request.getType().name())
                   .setStatus(request.getStatus().getCode());
        dictTypeMapper.insert(dictTypeNew);
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDict(DictRequest request) {
        Dict dict = dictManager.getDictByCodeWithDictType(request.getDictTypeId(), request.getCode());
        if (ObjectUtil.isNotNull(dict)) {
            throw new BizException(HttpResultCode.DATA_EXISTED);
        }
        Dict dictNew = JavaBeanUtils.map(request, Dict.class);
        dictNew.setStatus(request.getStatus().getCode());
        dictMapper.insert(dictNew);
    }
}

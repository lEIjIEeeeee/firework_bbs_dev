package com.wlj.firework.core.modular.common.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wlj.firework.core.modular.common.dao.DictMapper;
import com.wlj.firework.core.modular.common.dao.DictTypeMapper;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.common.manager.DictManager;
import com.wlj.firework.core.modular.common.model.entity.Dict;
import com.wlj.firework.core.modular.common.model.entity.DictType;
import com.wlj.firework.core.modular.common.model.request.*;
import com.wlj.firework.core.modular.common.util.JavaBeanUtils;
import com.wlj.firework.core.modular.common.util.PageUtils;
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
    public void editDictType(DictTypeRequest request) {
        DictType dictType = dictManager.getDictTypeByIdWithException(request.getId());
        DictType dictTypeByCode = dictManager.getDictTypeByCode(request.getCode());
        if (ObjectUtil.isNotNull(dictTypeByCode)
                && ObjectUtil.notEqual(request.getId(), dictTypeByCode.getId())) {
            throw new BizException(HttpResultCode.DATA_EXISTED, "存在相同编码的字典类型");
        }
        JavaBeanUtils.map(request, dictType);
        dictType.setType(request.getType().name())
                .setStatus(request.getStatus().getCode());
        dictTypeMapper.updateById(dictType);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDictType(DictTypeDeleteRequest request) {
        DictType dictType = dictManager.getDictTypeByIdWithException(request.getId());
        dictTypeMapper.deleteById(dictType.getId());
    }

    public DictType getDictTypeDetail(String id) {
        return dictManager.getDictTypeByIdWithException(id);
    }

    public Page<DictType> listPageDictType(DictTypeQueryRequest request) {
        if (StrUtil.isNotBlank(request.getKeywords())) {
            request.setKeywords(request.getKeywords().toUpperCase());
        }
        return dictTypeMapper.selectPage(PageUtils.createPage(request),
                Wrappers.lambdaQuery(DictType.class)
                        .and(StrUtil.isNotBlank(request.getKeywords()), wrapper -> wrapper.like(DictType::getCode, request.getKeywords())
                                                                                          .or()
                                                                                          .like(DictType::getName, request.getKeywords()))
                        .eq(request.getType() != null, DictType::getType, request.getType() != null ? request.getType().name() : null)
                        .eq(request.getStatus() != null, DictType::getStatus, request.getStatus() != null ? request.getStatus().getCode() : null)
                        .orderByAsc(DictType::getSort)
                        .orderByDesc(DictType::getUpdateTime));
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

    @Transactional(rollbackFor = Exception.class)
    public void editDict(DictRequest request) {
        DictType dictType = dictManager.getDictTypeByIdWithException(request.getDictTypeId());
        Dict dict = dictManager.getDictByIdWithException(request.getId());
        Dict dictByCode = dictManager.getDictByCodeWithDictType(dictType.getId(), request.getCode());
        if (ObjectUtil.isNotNull(dictByCode)
                && ObjectUtil.notEqual(request.getId(), dictByCode.getId())) {
            throw new BizException(HttpResultCode.DATA_EXISTED, "该字典类型下存在相同编码的字典值");
        }
        JavaBeanUtils.map(request, dict);
        dict.setStatus(request.getStatus().getCode());
        dictMapper.updateById(dict);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDict(DeleteDictRequest request) {
        Dict dict = dictManager.getDictByIdWithException(request.getId());
        dictMapper.deleteById(dict.getId());
    }

    public Dict getDictDetail(String id) {
        return dictManager.getDictById(id);
    }

    public Page<Dict> listPageDict(DictQueryRequest request) {
        if (StrUtil.isNotBlank(request.getKeywords())) {
            request.setKeywords(request.getKeywords().toUpperCase());
        }
        return dictMapper.selectPage(PageUtils.createPage(request),
                Wrappers.lambdaQuery(Dict.class)
                        .eq(Dict::getDictTypeId, request.getDictTypeId())
                        .and(StrUtil.isNotBlank(request.getKeywords()), wrapper -> wrapper.like(Dict::getCode, request.getKeywords())
                                                                                          .or()
                                                                                          .like(Dict::getName, request.getKeywords()))
                        .eq(request.getStatus() != null, Dict::getStatus, request.getStatus() != null ? request.getStatus().getCode() : null)
                        .orderByAsc(Dict::getSort)
                        .orderByDesc(Dict::getUpdateTime));
    }

}

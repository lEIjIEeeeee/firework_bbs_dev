package com.wlj.firework.core.modular.common.manager;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wlj.firework.core.modular.common.dao.DictMapper;
import com.wlj.firework.core.modular.common.dao.DictTypeMapper;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.common.model.entity.Dict;
import com.wlj.firework.core.modular.common.model.entity.DictType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DictManager {

    private final DictTypeMapper dictTypeMapper;
    private final DictMapper dictMapper;

    public DictType getDictTypeById(String id) {
        return dictTypeMapper.selectById(id);
    }

    public DictType getDictTypeByIdWithException(String id) {
        DictType dictType = dictTypeMapper.selectById(id);
        if (ObjectUtil.isNull(dictType)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTS);
        }
        return dictType;
    }

    public DictType getDictTypeByCode(String code) {
        return dictTypeMapper.selectOne(Wrappers.lambdaQuery(DictType.class)
                                                .eq(DictType::getCode, code));
    }

    public Dict getDictById(String id) {
        return dictMapper.selectById(id);
    }

    public Dict getDictByIdWithException(String id) {
        Dict dict = dictMapper.selectById(id);
        if (ObjectUtil.isNull(dict)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTS);
        }
        return dict;
    }

    public Dict getDictByCodeWithDictType(String dictTypeId, String code) {
        return dictMapper.selectOne(Wrappers.lambdaQuery(Dict.class)
                                            .eq(Dict::getDictTypeId, dictTypeId)
                                            .eq(Dict::getCode, code));
    }

}

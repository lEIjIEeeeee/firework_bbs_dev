package com.wlj.firework.core.modular.demo.manager;

import cn.hutool.core.util.ObjectUtil;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.demo.dao.DemoMapper;
import com.wlj.firework.core.modular.demo.model.entity.Demo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DemoManager {

    private final DemoMapper demoMapper;

    public Demo getById(String id) {
        return demoMapper.selectById(id);
    }

    public Demo getByIdWithException(String id) {
        Demo demo = getById(id);
        if (ObjectUtil.isNull(demo)) {
            throw new BizException(HttpResultCode.DATA_NOT_EXISTS);
        }
        return demo;
    }

}

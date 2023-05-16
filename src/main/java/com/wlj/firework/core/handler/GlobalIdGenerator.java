package com.wlj.firework.core.handler;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.wlj.firework.core.modular.common.constant.SystemConstants;
import com.wlj.firework.core.modular.common.util.NoGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class GlobalIdGenerator implements IdentifierGenerator {

    @Lazy
    @Autowired
    private NoGenerateUtils noGenerateUtils;

    @Override
    public Number nextId(Object entity) {
        //TODO 雪花算法
        return null;
    }

    @Override
    public String nextUUID(Object entity) {
        long id = noGenerateUtils.generateByLeaf();
        return SystemConstants.APP_ID + StrUtil.padPre(String.valueOf(id), 9, "0");
    }

}

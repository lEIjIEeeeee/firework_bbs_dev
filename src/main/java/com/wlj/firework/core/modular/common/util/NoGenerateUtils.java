package com.wlj.firework.core.modular.common.util;

import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.wlj.firework.core.modular.segment.enums.SegmentKey;
import com.wlj.firework.core.modular.segment.enums.Status;
import com.wlj.firework.core.modular.segment.model.Result;
import com.wlj.firework.core.modular.segment.service.SegmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NoGenerateUtils {

    @Value("${snowflake.workId}")
    private long workerId;
    private final SegmentService segmentService;

    public Snowflake getSnowflake() {
        // 不缓存系统时钟，并发高可以考虑缓存
        return Singleton.get(Snowflake.class, workerId, false);
    }

    public long generateBySnowflake() {
        return getSnowflake().nextId();
    }

    public long generateByLeaf() {
        return generateByLeaf(SegmentKey.LEAF_DEFAULT);
    }

    public long generateByLeaf(SegmentKey key) {
        return generateByLeaf(key, null);
    }

    public long generateByLeaf(SegmentKey key, Integer randomUpper) {
        int num = 1;
        if (ObjectUtil.isNotNull(randomUpper)) {
            if (randomUpper > 1) {
                num = RandomUtil.randomInt(randomUpper) + 1;
            } else {
                throw new IllegalArgumentException("随机上界必须大于 1");
            }
        }
        long id = 0L;
        while (num-- > 0) {
            Result result = segmentService.getId(key.getCode());
            if (result.getStatus() != Status.SUCCESS) {
                throw new RuntimeException("leaf 生成 id 异常 id:" + result.getId());
            }
            id = result.getId();
        }
        return id;
    }

}

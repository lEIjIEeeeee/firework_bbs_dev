package com.wlj.firework.core.modular.segment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SegmentKey {
    //
    LEAF_DEFAULT("leaf-segment-test", 0L, 100),
    RANDOM_NUM("random_num", 10000000L, 100),
    PRODUCT_CODE("product_code", 100000L, 100),
    ;

    private final String code;
    private final long maxId;
    private final int step;
}

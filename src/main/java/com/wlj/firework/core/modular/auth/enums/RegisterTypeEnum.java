package com.wlj.firework.core.modular.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegisterTypeEnum {
    NORMAL(0, "游客"),
    MEMBER(1, "会员");

    private final Integer code;
    private final String name;
}

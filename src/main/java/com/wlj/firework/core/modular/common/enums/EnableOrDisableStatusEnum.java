package com.wlj.firework.core.modular.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnableOrDisableStatusEnum {
    ENABLE(0, "启用"),
    DISABLE(1, "禁用");

    private final Integer code;
    private final String name;
}

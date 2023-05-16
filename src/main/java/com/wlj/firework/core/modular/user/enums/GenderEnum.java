package com.wlj.firework.core.modular.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {
    //女
    F(0, "F"),
    //男
    M(1, "M");

    private final Integer code;
    private final String name;
}

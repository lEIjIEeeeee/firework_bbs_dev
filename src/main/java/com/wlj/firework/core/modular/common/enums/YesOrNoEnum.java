package com.wlj.firework.core.modular.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesOrNoEnum {

    N(0, "否", false),
    Y(1, "是", true),
    ;

    private final Integer code;
    private final String name;
    private final Boolean flag;

}

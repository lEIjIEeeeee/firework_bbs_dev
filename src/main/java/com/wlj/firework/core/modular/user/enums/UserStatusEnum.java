package com.wlj.firework.core.modular.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    //正常
    NORMAL(0, "正常"),
    //限制
    LIMIT(1, "限制"),
    //冻结
    FREEZE(2, "冻结"),
    //删除
    DELETE(3, "删除");

    private final Integer code;
    private final String name;
}

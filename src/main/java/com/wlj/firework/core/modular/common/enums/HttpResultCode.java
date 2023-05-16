package com.wlj.firework.core.modular.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpResultCode {
    //
    SUCCESS(0, "成功"),

    SYSTEM_ERROR(999, "系统异常"),

    PARAM_VALIDATE_FAILED(1000, "参数校验失败"),
    TOKEN_VALIDATE_FAILED(1001, "Token校验失败"),

    USER_PASSWORD_VALIDATE_FAILED(2000, "账号密码校验失败"),
    USER_ACCOUNT_NOT_EXIST(2002, "用户账号不存在"),

    DATA_NOT_EXISTS(3000, "数据不存在"),
    DATA_EXISTED(3006, "数据已存在"),
    BIZ_EXCEPTION(3011, "业务异常"),
    ;

    private final Integer code;
    private final String message;

}

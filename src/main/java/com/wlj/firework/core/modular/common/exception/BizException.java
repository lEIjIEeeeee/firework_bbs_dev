package com.wlj.firework.core.modular.common.exception;

import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import lombok.Data;

@Data
public class BizException extends RuntimeException{

    private static final long serialVersionUID = -3080038779618382063L;

    private final HttpResultCode httpResultCode;

    public BizException(HttpResultCode httpResultCode) {
        super(httpResultCode.getMessage());
        this.httpResultCode = httpResultCode;
    }

    public BizException(HttpResultCode httpResultCode, String message) {
        super(message);
        this.httpResultCode = httpResultCode;
    }

    //TODO 其他抛出异常方式
}

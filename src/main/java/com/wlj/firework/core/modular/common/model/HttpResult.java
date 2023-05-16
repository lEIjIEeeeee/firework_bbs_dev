package com.wlj.firework.core.modular.common.model;

import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpResult<T> implements Serializable {

    private static final long serialVersionUID = 9130000122365425796L;

    @ApiModelProperty(value = "状态编码")
    private Integer code;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public static <T> HttpResult<T> success() {
        return HttpResult.<T>builder()
                         .code(HttpResultCode.SUCCESS.getCode())
                         .message(HttpResultCode.SUCCESS.getMessage())
                         .build();
    }

    public static <T> HttpResult<T> success(T data) {
        return HttpResult.<T>builder()
                         .code(HttpResultCode.SUCCESS.getCode())
                         .message(HttpResultCode.SUCCESS.getMessage())
                         .data(data)
                         .build();
    }

    public static <T> HttpResult<T> failure(HttpResultCode httpResultCode) {
        return HttpResult.<T>builder()
                         .code(httpResultCode.getCode())
                         .message(httpResultCode.getMessage())
                         .build();
    }

    public static <T> HttpResult<T> failure(HttpResultCode httpResultCode, String message) {
        return HttpResult.<T>builder()
                         .code(httpResultCode.getCode())
                         .message(message)
                         .build();
    }

}

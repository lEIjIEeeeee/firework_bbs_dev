package com.wlj.firework.core.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.model.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public HttpResult<Void> handleParamsNoValidException(Exception e) {
        log.error("handleParamsNoValidException:", e);
        List<FieldError> errorList = new ArrayList<>();
        if (e instanceof MethodArgumentNotValidException) {
            errorList = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors();
        }
        if (e instanceof BindException) {
            errorList = ((BindException)e).getFieldErrors();
        }

        if (ArrayUtil.isNotEmpty(errorList)) {
            StringBuilder errorMsg = new StringBuilder("校验失败：");
            for (FieldError fieldError : errorList) {
                errorMsg.append(fieldError.getField())
                        .append("：")
                        .append(fieldError.getDefaultMessage())
                        .append("，");
            }
            return HttpResult.failure(HttpResultCode.PARAM_VALIDATE_FAILED, String.valueOf(errorMsg));
        } else {
            return HttpResult.failure(HttpResultCode.PARAM_VALIDATE_FAILED);
        }
    }
}

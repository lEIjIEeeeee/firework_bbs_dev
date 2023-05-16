package com.wlj.firework.core.context;

import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import com.wlj.firework.core.modular.common.model.dto.LoginUser;
import org.springframework.core.NamedThreadLocal;

import java.util.Optional;

public class ContextUtils {

    private final static String CONTEXT = "context";

    private static final NamedThreadLocal<Context> contextContainer = new NamedThreadLocal<>(CONTEXT);

    public static void setContext(Context context) {
        contextContainer.set(context);
    }

    public static Context getContext() {
        return contextContainer.get();
    }

    public static LoginUser getCurrentUser() {
        return Optional.ofNullable(contextContainer.get())
                       .map(Context::getLoginUser)
                       .orElseThrow(() -> new BizException(HttpResultCode.SYSTEM_ERROR, "用户未登录！"));
    }

    public static String getCurrentUserId() {
        return Optional.ofNullable(getCurrentUser())
                       .map(LoginUser::getId)
                       .orElseThrow(() -> new BizException(HttpResultCode.SYSTEM_ERROR, "获取当前用户ID失败！"));
    }

    public static void clear() {
        contextContainer.remove();
    }

}

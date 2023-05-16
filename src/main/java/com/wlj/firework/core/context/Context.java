package com.wlj.firework.core.context;

import com.wlj.firework.core.modular.common.model.dto.LoginUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Context {

    private LoginUser loginUser;

}

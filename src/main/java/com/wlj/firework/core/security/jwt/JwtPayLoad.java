package com.wlj.firework.core.security.jwt;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayLoad {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户账号
     */
    private String account;

    public Map<String, Object> toMap() {
        return BeanUtil.beanToMap(this);
    }

    public static JwtPayLoad toBean(Map<String, Object> map) {
        return BeanUtil.toBean(map, JwtPayLoad.class);
    }

}

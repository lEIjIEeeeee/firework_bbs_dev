package com.wlj.firework.core.modular.demo.service;

import com.wlj.firework.core.modular.demo.model.request.SetKeyRequest;
import com.wlj.firework.core.modular.common.util.JedisUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class JedisSentinelPoolDemoService {

    private final JedisUtils redisUtils;

    public void setKey(SetKeyRequest request) {
        redisUtils.set(request.getKey(), request.getValue());
    }

    public String getValue(String key) {
        return redisUtils.get(key).toString();
    }

}

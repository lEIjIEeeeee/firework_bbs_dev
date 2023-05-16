package com.wlj.firework.core.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.List;

@Slf4j
@Configuration
public class JedisConfig {

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.timeout}")
    private Integer timeout;

    @Value("${spring.redis.jedis.pool.min-idle}")
    private Integer minIdle;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.pool.max-active}")
    private Integer maxActive;

    @Value("${jedisPool.masterName}")
    private String jedisPoolMasterName;

    @Value("${jedisPool.sentinels}")
    private String jedisPoolSentinels;

    @Bean
    public JedisSentinelPool jedisSentinelPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setTestOnBorrow(Boolean.TRUE);
        jedisPoolConfig.setTestWhileIdle(Boolean.TRUE);
        jedisPoolConfig.setTestOnReturn(Boolean.TRUE);

        List<String> sentinels = StrUtil.split(jedisPoolSentinels, StrUtil.C_COMMA);
        return new JedisSentinelPool(jedisPoolMasterName, CollUtil.newHashSet(sentinels), jedisPoolConfig, timeout, password, database);
    }

}

package com.wlj.firework.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wlj.firework.core.modular.common.constant.SymbolsConstants;
import com.wlj.firework.core.modular.common.enums.HttpResultCode;
import com.wlj.firework.core.modular.common.exception.BizException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

import javax.annotation.Resource;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Component
public class JedisUtils {

    private final JedisSentinelPool jedisSentinelPool;
    private final ObjectMapper objectMapper;

    /**
     * 默认key过期时间/秒
     */
    private static final long defaultTimeOut = 60;

    /**
     * 默认等待时间/毫秒
     */
    private static final long defaultWaitTime = 100;

    private static final Long SETNX_TRUE = 1L;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    public Jedis getJedis() {
        return jedisSentinelPool.getResource();
    }

    /**
     * 指定缓存失效时间
     */
    public void expire(String key, int time) {
        try (Jedis jedis = getJedis()) {
            if (time > 0) {
                jedis.expire(key, time);
            }
        }
    }

    /**
     * 根据key 获取过期时间
     */
    public long getExpire(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.ttl(key);
        }
    }

    /**
     * 判断key是否存在
     */
    public boolean hasKey(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.exists(key);
        }
    }

    /**
     * 删除缓存
     */
    public void del(String... key) {
        try (Jedis jedis = getJedis()) {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    jedis.del(key[0]);
                } else {
                    jedis.del(key);
                }
            }
        }
    }

    public String getStr(String key) {
        Object o = get(key);
        return o != null ? o.toString() : null;
    }

    public Integer getInt(String key) {
        Object o = get(key);
        return o != null ? (Integer) o : null;
    }

    public <T> T getObjWithClass(String key, Class<T> objClass) {
        String objStr = getStr(key);
        if (StrUtil.isBlank(objStr)) {
            return null;
        }
        try {
            return objectMapper.readValue(objStr, objClass);
        } catch (Exception e) {
            log.error("jedisUtils-getObjWithClass-exception:", e);
            throw new BizException(HttpResultCode.BIZ_EXCEPTION);
        }
    }

    /**
     * 普通缓存获取
     */
    public Object get(String key) {
        try (Jedis jedis = getJedis()) {
            return key == null ? null : jedis.get(key);
        }
    }

    /**
     * 普通缓存放入
     */
    public void set(String key, Object value) {
        set(key, String.valueOf(value), 0);
    }

    /**
     * 普通缓存放入
     */
    public void setWithDay(String key, Object value, int days) {
        set(key, String.valueOf(value), days * 24 * 60 * 60);
    }

    /**
     * 普通缓存放入并设置时间
     */
    public void set(String key, String value, int time) {
        try (Jedis jedis = getJedis()) {
            if (time > 0) {
                jedis.setex(key, time, value);
            } else {
                jedis.set(key, value);
            }
        }
    }

    public void setObj(String key, Object value) {
        setObj(key, value, 0);
    }

    public void setObjWithDay(String key, Object value, int days) {
        setObj(key, value, days * 24 * 60 * 60);
    }

    public void setObj(String key, Object value, int time) {
        try {
            set(key, objectMapper.writeValueAsString(value), time);
        } catch (JsonProcessingException e) {
            log.error("JedisUtils-setObj", e);
            throw new BizException(HttpResultCode.BIZ_EXCEPTION);
        }
    }

    public Boolean setNx(String key, String value, int time) {
        try (Jedis jedis = getJedis()) {
            if (SETNX_TRUE.equals(jedis.setnx(key, value))) {
                expire(key, time);
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
    }

    /**
     * 递增
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            log.error("JedisUtils-incr-递增因子必须大于0");
            throw new BizException(HttpResultCode.BIZ_EXCEPTION);
        }
        try (Jedis jedis = getJedis()) {
            return jedis.incrBy(key, delta);
        }
    }

    /**
     * 递减
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            log.error("JedisUtils-decr-递增因子必须大于0");
            throw new BizException(HttpResultCode.BIZ_EXCEPTION);
        }
        try (Jedis jedis = getJedis()) {
            return jedis.decrBy(key, delta);
        }
    }

//    @NotNull
//    public Set<Object> getPageZ(String key, Long pageNo, Long pageSize) {
//        Set<Object> set = redisTemplate.opsForZSet().reverseRange(key, (pageNo - 1) * pageSize, pageNo * pageSize - 1);
//        if (ObjectUtil.isNull(set)) {
//            return CollUtil.newHashSet();
//        }
//        return set;
//    }

//    @NotNull
//    public Set<String> getAllZ(String key) {
//        Set<Object> set = redisTemplate.opsForZSet().range(key, 0, -1);
//        if (ObjectUtil.isNull(set)) {
//            return CollUtil.newHashSet();
//        }
//        return set.stream().map(String::valueOf).collect(Collectors.toSet());
//    }

//    public void zAdd(String key, String value, double score) {
//        redisTemplate.opsForZSet().add(key, value, score);
//    }
//
//    public void zDel(String key, Object... member) {
//        redisTemplate.opsForZSet().remove(key, member);
//    }

    /**
     * HashGet
     */
    public Object hget(String key, String item) {
        try (Jedis jedis = getJedis()) {
            return jedis.hget(key, item);
        }
    }

    /**
     * 获取hashKey对应的所有键值
     */
    public Map<String, String> hmget(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.hgetAll(key);
        }
    }

    /**
     * HashSet
     */
    public void hmset(String key, Map<String, String> map) {
        try (Jedis jedis = getJedis()) {
            jedis.hmset(key, map);
        }
    }

    /**
     * HashSet 并设置时间
     */
    public void hmset(String key, Map<String, String> map, int time) {
        hmset(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    public void hsetObj(String key, String item, Object obj) {
        try {
            hset(key, item, objectMapper.writeValueAsString(obj));
        } catch (JsonProcessingException e) {
            log.error("JedisUtils-hsetObj:[{}]", e);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     */
    public void hset(String key, String item, String value) {
        hset(key, item, value, 0);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     */
    public void hset(String key, String item, String value, int time) {
        try (Jedis jedis = getJedis()) {
            jedis.hset(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
        }
    }

    /**
     * 删除hash表中的值
     */
    public void hdel(String key, String... item) {
        try (Jedis jedis = getJedis()) {
            jedis.hdel(key, item);
        }
    }

    /**
     * 判断hash表中是否有该项的值
     */
    public boolean hHasKey(String key, String item) {
        try (Jedis jedis = getJedis()) {
            return jedis.hexists(key, item);
        }
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     */
    public Long hincr(String key, String item, long by) {
        try (Jedis jedis = getJedis()) {
            return jedis.hincrBy(key, item, by);
        }
    }

    /**
     * hash递减
     */
    public double hdecr(String key, String item, long by) {
        try (Jedis jedis = getJedis()) {
            return hincr(key, item, MyNumberUtils.negative(by));
        }
    }

    /**
     * 根据key获取Set中的所有值
     */
    public Set<String> sGet(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.smembers(key);
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     */
    public boolean sIsMember(String key, String value) {
        try (Jedis jedis = getJedis()) {
            return jedis.sismember(key, value);
        }
    }

    /**
     * 将数据放入set缓存
     */
    public long sSet(String key, String... values) {
        try (Jedis jedis = getJedis()) {
            return jedis.sadd(key, values);
        }
    }

    /**
     * 将set数据放入缓存
     */
    public long sSetAndTime(String key, int time, String... values) {
        long count = sSet(key, values);
        if (time > 0) {
            expire(key, time);
        }
        return count;
    }

    /**
     * 获取set缓存的长度
     */
    public long sGetSetSize(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.scard(key);
        }
    }

    /**
     * 移除值为value的
     */
    public long setRemove(String key, String... values) {
        try (Jedis jedis = getJedis()) {
            return jedis.srem(key, values);
        }
    }


    public <T> List<T> lGetObj(String key, long start, long end, Class<T> clazz) {
        List<String> dataStrList = lGet(key, start, end);
        if (CollUtil.isEmpty(dataStrList)) {
            return Collections.emptyList();
        }

        // TODO-JC 待优化
        return JSON.parseArray(String.valueOf(dataStrList), clazz);
    }

    /**
     * 获取list缓存的内容
     */
    public List<String> lGet(String key, long start, long end) {
        try (Jedis jedis = getJedis()) {
            return jedis.lrange(key, start, end);
        }
    }

    /**
     * 获取list缓存的长度
     */
    public long lGetListSize(String key) {
        try (Jedis jedis = getJedis()) {
            return jedis.llen(key);
        }
    }

    /**
     * 通过索引 获取list中的值
     */
    public String lGetIndex(String key, long index) {
        try (Jedis jedis = getJedis()) {
            return jedis.lindex(key, index);
        }
    }

    /**
     * 将list放入缓存
     */
    public void lSet(String key, List<String> value) {
        lSet(key, value, 0);
    }

    /**
     * 将list放入缓存
     */
    public void lSet(String key, List<String> value, int time) {
        lSet(key, value.toArray(new String[]{}), time);
    }

    public void lSetObj(String key, Object obj) {
        lSetObj(key, obj, 0);
    }

    public void lSetObj(String key, Object obj, int time) {
        try {
            lSet(key, Collections.singletonList(objectMapper.writeValueAsString(obj)), time);
        } catch (JsonProcessingException e) {
            log.error("JedisUtils-lSetObj:[{}]", e);
            throw new BizException(HttpResultCode.BIZ_EXCEPTION);
        }
    }

    /**
     * 将list放入缓存
     */
    public void lSet(String key, String... value) {
        lSet(key, value, 0);
    }

    /**
     * 将list放入缓存
     */
    public void lSet(String key, String[] value, int time) {
        try (Jedis jedis = getJedis()) {
            jedis.rpush(key, value);

            if (time > 0) {
                expire(key, time);
            }
        }
    }

    /**
     * 根据索引修改list中的某条数据
     */
    public void lUpdateIndex(String key, Integer index, String value) {
        try (Jedis jedis = getJedis()) {
            jedis.lset(key, index, value);
        }
    }

    /**
     * 移除N个值为value
     */
    public long lRemove(String key, Long count, String value) {
        try (Jedis jedis = getJedis()) {
            return jedis.lrem(key, count, value);
        }
    }

    /**
     * 根据正则获得所有key
     */
    public Set<String> getKeys(String pattern) {
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keysTmp = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder()
                    .match(SymbolsConstants.ASTERISK + pattern + SymbolsConstants.ASTERISK)
                    .count(1000)
                    .build());
            while (cursor.hasNext()) {
                keysTmp.add(new String(cursor.next()));
            }
            return keysTmp;
        });
    }
}

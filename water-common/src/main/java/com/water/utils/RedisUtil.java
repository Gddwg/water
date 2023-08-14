package com.water.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.water.constans.RedisConstants.CACHE_NULL_TTL;


public class RedisUtil {
    private static StringRedisTemplate stringRedisTemplate;

    public static StringRedisTemplate getStringRedisTemplate() {
        return stringRedisTemplate;
    }

    public RedisUtil(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public static String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public static void set(String key, Object value, Long time, TimeUnit unit){
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    public static void set(String key, Object value){
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
    }

    public static <R ,ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type, Function<ID, R> dbfallback, Long time, TimeUnit unit){
        String key = keyPrefix + id;
        String json = get(key);
        if(StrUtil.isNotBlank(json)){
            return JSONUtil.toBean(json, type);
        }
        if(json != null){
            return null;
        }
        R r = dbfallback.apply(id);
        if(r == null){
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        set(key,r,time,unit);
        return r;
    }
}

package com.water.redis;

import cn.hutool.json.JSONUtil;
import com.water.constans.RedisConstants;
import com.water.entity.TokenBind;
import com.water.utils.RedisUtil;

import java.util.concurrent.TimeUnit;

public class TokenRedis {
    public static TokenBind getTokenBind(String token){
        String tokenBind = RedisUtil.get(RedisConstants.USER_TOKEN_KEY + token);
        return JSONUtil.toBean(tokenBind,TokenBind.class);
    }
    public static void refreshToken(Long id,String token){
        RedisUtil.getStringRedisTemplate().expire(RedisConstants.USER_TOKEN_KEY + token, RedisConstants.USER_TOKEN_TTL, TimeUnit.DAYS);
        RedisUtil.getStringRedisTemplate().expire(RedisConstants.USER_TOKEN_KEY + id, RedisConstants.USER_TOKEN_TTL, TimeUnit.DAYS);
    }
    public static void setTokenBind(String token,TokenBind tokenBind){
        RedisUtil.set(RedisConstants.USER_TOKEN_KEY + token, tokenBind, RedisConstants.USER_TOKEN_TTL, TimeUnit.DAYS);
    }
    public static void setToken(Long id,String token){
        RedisUtil.set(RedisConstants.USER_TOKEN_KEY + id, token, RedisConstants.USER_TOKEN_TTL,TimeUnit.DAYS);
    }

    public static String getToken(Long id) {
        return RedisUtil.get(RedisConstants.USER_TOKEN_KEY + id);
    }

    public static void remove(Long id, String oldToken) {
        RedisUtil.getStringRedisTemplate().delete(RedisConstants.USER_TOKEN_KEY + id);
        RedisUtil.getStringRedisTemplate().delete(RedisConstants.USER_TOKEN_KEY + oldToken);

    }
}

package com.water.redis;

import com.alibaba.fastjson.JSONObject;
import com.water.constans.RedisConstants;
import com.water.entity.TokenBind;
import com.water.utils.RedisUtil;
import org.springframework.boot.autoconfigure.cache.CacheProperties;

import java.util.concurrent.TimeUnit;

public class TokenRedis {
    public static TokenBind getTokenBind(String token){
        String tokenBind = RedisUtil.get(RedisConstants.USER_TOKEN_KEY + token);
        return (TokenBind) JSONObject.parse(tokenBind);
    }
    public static void refreshToken(String token){
        RedisUtil.getStringRedisTemplate().expire(RedisConstants.USER_TOKEN_KEY + token, RedisConstants.USER_TOKEN_TTL, TimeUnit.DAYS);
    }
    public static void setTokenBind(String token,TokenBind tokenBind){
        RedisUtil.set(RedisConstants.USER_TOKEN_KEY + token, tokenBind, RedisConstants.USER_TOKEN_TTL, TimeUnit.DAYS);
    }
}

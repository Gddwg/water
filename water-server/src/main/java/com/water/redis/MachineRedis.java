package com.water.redis;

import com.water.constans.RedisConstants;
import com.water.utils.RedisUtil;

import java.util.concurrent.TimeUnit;

public class MachineRedis {
    public static void setMachinePush(String key, Object info){
        RedisUtil.set(RedisConstants.MACHINE_PUSH_KEY + key, info, RedisConstants.MACHINE_PUSH_TTL, TimeUnit.SECONDS);
    }

    public static String getMachinePush(String key) {
        return RedisUtil.get(RedisConstants.MACHINE_PUSH_KEY + key);
    }
}

package com.water.redis;

import com.water.constans.RedisConstans;
import com.water.utils.RedisUtil;

import java.util.concurrent.TimeUnit;

public class MachineRedis {
    public static void setMachinePush(String key, Object info){
        RedisUtil.set(RedisConstans.MACHINE_PUSH_KEY + key, info, RedisConstans.MACHINE_PUSH_TTL, TimeUnit.SECONDS);
    }

    public static String getMachinePush(String key) {
        return RedisUtil.get(RedisConstans.MACHINE_PUSH_KEY + key);
    }
}

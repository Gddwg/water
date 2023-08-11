package com.water.redis;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.water.constans.RedisConstans;
import com.water.entity.WaterMap;
import com.water.utils.RedisUtil;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.water.constans.RedisConstans.CACHE_NULL_TTL;

public class MapRedis {
    public static WaterMap getByMap(WaterMap waterMap, Wrapper wrapper, Function<Wrapper, List> dbfallback) {
        String key = RedisConstans.MAP_INFO_KEY + waterMap.getMapName() + "-" + waterMap.getFloor();
        String res = RedisUtil.get(key);
        if(res != null){
            return JSONUtil.toBean(res, WaterMap.class);
        }
        List<WaterMap> r = dbfallback.apply(wrapper);
        if(r == null){
            RedisUtil.set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        WaterMap m = r.get(0);
        RedisUtil.set(key,m,RedisConstans.MAP_INFO_TTL,TimeUnit.MINUTES);
        return m;
    }
}

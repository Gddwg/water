package com.water.redis;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.water.constans.RedisConstans;
import com.water.entity.Map;
import com.water.entity.Marker;
import com.water.utils.RedisUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.water.constans.RedisConstans.CACHE_NULL_TTL;

public class MarkerRedis {
    public static List<Marker> List(Marker marker, Wrapper wrapper, Function<Wrapper, List> dbfallback) {
        String key = RedisConstans.MARKER_INFO_KEY + marker.getMapName() + "-" + marker.getFloor();
        String res = RedisUtil.get(key);
        if(res != null){
            return (List<Marker>) JSONObject.parse(res);
        }
        List<Marker> r = dbfallback.apply(wrapper);
        if(r == null){
            RedisUtil.set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        RedisUtil.set(key,r,RedisConstans.MARKER_INFO_TTL, TimeUnit.MINUTES);
        return r;
    }
}

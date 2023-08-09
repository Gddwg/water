package com.water.redis;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.water.constans.RedisConstans;
import com.water.entity.Map;
import com.water.mapper.MapMapper;
import com.water.utils.RedisUtil;
import org.yaml.snakeyaml.events.Event;


import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.water.constans.RedisConstans.CACHE_NULL_TTL;

public class MapRedis {
    public static Map getByMap(Map map,Wrapper wrapper, Function<Wrapper, List> dbfallback) {
        String key = RedisConstans.MAP_INFO_KEY + map.getMapName() + "-" + map.getFloor();
        String res = RedisUtil.get(key);
        if(res != null){
            return JSONUtil.toBean(res,Map.class);
        }
        List<Map> r = dbfallback.apply(wrapper);
        if(r == null){
            RedisUtil.set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null;
        }
        Map m = r.get(0);
        RedisUtil.set(key,m,RedisConstans.MAP_INFO_TTL,TimeUnit.MINUTES);
        return m;
    }
}

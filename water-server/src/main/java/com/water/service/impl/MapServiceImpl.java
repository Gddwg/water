package com.water.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water.entity.Map;
import com.water.mapper.MapMapper;
import com.water.redis.MapRedis;
import com.water.rest.RestMap;
import com.water.service.MapService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MapServiceImpl implements MapService {
    @Resource
    MapMapper mapMapper;
    @Resource
    RestMap restMap;
    @Override
    public Map getMapPoint(Map map) {
        QueryWrapper<Map> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Map::getMapName,map.getMapName()).eq(Map::getFloor,map.getFloor());
        Map mapPoint = MapRedis.getByMap(map,queryWrapper, mapMapper::selectList);
        return mapPoint;
    }

    @Override
    public void addMapPoint(String address) {
        Map mapPoint = restMap.getMapPoint(address);
        mapMapper.insert(mapPoint);
    }
}

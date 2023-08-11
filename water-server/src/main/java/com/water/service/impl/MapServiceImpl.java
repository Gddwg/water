package com.water.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water.entity.WaterMap;
import com.water.mapper.MapMapper;
import com.water.redis.MapRedis;
import com.water.rest.RestMap;
import com.water.service.MapService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MapServiceImpl implements MapService {
    @Resource
    MapMapper mapMapper;
    @Resource
    RestMap restMap;
    @Override
    public WaterMap getMapPoint(WaterMap waterMap) {
        QueryWrapper<WaterMap> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WaterMap::getMapName, waterMap.getMapName()).eq(WaterMap::getFloor, waterMap.getFloor());
        WaterMap waterMapPoint = MapRedis.getByMap(waterMap,queryWrapper, mapMapper::selectList);
        return waterMapPoint;
    }

    @Override
    public void addMapPoint(String address) {
        WaterMap waterMapPoint = restMap.getMapPoint(address);
        mapMapper.insert(waterMapPoint);
    }
}

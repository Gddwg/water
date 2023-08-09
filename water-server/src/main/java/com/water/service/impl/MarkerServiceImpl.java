package com.water.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water.entity.Map;
import com.water.entity.Marker;
import com.water.mapper.MarkerMapper;
import com.water.redis.MarkerRedis;
import com.water.rest.RestMarker;
import com.water.service.MarkerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MarkerServiceImpl implements MarkerService {

    @Resource
    RestMarker restMarker;
    @Resource
    MarkerMapper markerMapper;

    @Override
    public List<Marker> getMarkers(String mapName, Integer floor) {
        Marker marker = new Marker();
        marker.setMapName(mapName);
        marker.setFloor(floor);
        QueryWrapper<Marker> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Marker::getMapName,mapName).eq(Marker::getFloor,floor);
        return MarkerRedis.List(marker,queryWrapper,markerMapper::selectList);
    }

    @Override
    public void addMarker(String address,String mapName) {
        List<Marker> markers = restMarker.getMarkers(address,mapName);
        markers.forEach(markerMapper::insert);
    }

    @Override
    public void toMarker(String address,Integer count,List<String> markers) {
        restMarker.toMarker(address,count,markers);
    }
}

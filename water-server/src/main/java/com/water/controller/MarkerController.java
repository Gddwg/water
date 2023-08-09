package com.water.controller;


import com.water.constans.BaseConstans;
import com.water.dto.BaseDTO;

import com.water.dto.MoveDTO;
import com.water.dto.PointDTO;
import com.water.entity.Marker;
import com.water.rest.RestMarker;
import com.water.result.Result;
import com.water.service.MarkerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/marker")
public class MarkerController {
    @Resource
    MarkerService markerService;
    @PostMapping("get")
    public Result<List<Marker>> getMarker(@RequestBody PointDTO pointDTO){
        String mapName = pointDTO.getMapName();
        Integer floor = pointDTO.getFloor();
        return Result.success(markerService.getMarkers(mapName,floor));
    }
    @PostMapping("add")
    public Result addMarker(@RequestBody PointDTO pointDTO){
        String address = pointDTO.getAddress();
        String mapName = pointDTO.getMapName();
        markerService.addMarker(address,mapName);
        return Result.success(BaseConstans.SECCESS);
    }
    @PostMapping("move")
    public Result move(@RequestBody MoveDTO moveDTO){
        Integer count = moveDTO.getCount();
        String address = moveDTO.getAddress();
        List<String> markers = moveDTO.getMarkers();
        markerService.toMarker(address,count,markers);
        return Result.success(BaseConstans.SECCESS);
    }
}
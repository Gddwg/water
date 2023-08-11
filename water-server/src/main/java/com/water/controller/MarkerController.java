package com.water.controller;


import com.water.constans.BaseConstans;
import com.water.dto.MoveDTO;
import com.water.dto.PointDTO;
import com.water.entity.Marker;
import com.water.result.Result;
import com.water.service.MarkerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        int floor = pointDTO.getFloor();
        return Result.success(markerService.getMarkers(mapName,floor));
    }
    @PostMapping("scan")
    public Result scanMarker(@RequestBody PointDTO pointDTO){
        String address = pointDTO.getAddress();
        String mapName = pointDTO.getMapName();
        markerService.scanMarker(address,mapName);
        return Result.success(BaseConstans.SECCESS);
    }
    @PostMapping("move")
    public Result move(@RequestBody MoveDTO moveDTO){
        int count = moveDTO.getCount();
        String address = moveDTO.getAddress();
        List<String> markers = moveDTO.getMarkers();
        markerService.toMarker(address,count,markers);
        return Result.success(BaseConstans.SECCESS);
    }
}

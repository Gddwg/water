package com.water.controller;


import com.water.constans.BaseConstants;
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

    /**
     * 获取指定地图的所有点位
     * @param pointDTO
     * @return
     */
    @PostMapping("get")
    public Result<List<Marker>> getMarker(@RequestBody PointDTO pointDTO){
        String mapName = pointDTO.getMapName();
        int floor = pointDTO.getFloor();
        return Result.success(markerService.getMarkers(mapName,floor));
    }

    /**
     * 扫描机器人中指定地图的所有点位存入mysql
     * @param pointDTO
     * @return
     */
    @PostMapping("scan")
    public Result<String> scanMarker(@RequestBody PointDTO pointDTO){
        String address = pointDTO.getAddress();
        String mapName = pointDTO.getMapName();
        markerService.scanMarker(address,mapName);
        return Result.success(BaseConstants.SUCCESS);
    }

    /**
     * 让机器人进行点位间移动
     * @param moveDTO
     * @return
     */
    @PostMapping("move")
    public Result<String> move(@RequestBody MoveDTO moveDTO){
        int count = moveDTO.getCount();
        String address = moveDTO.getAddress();
        List<String> markers = moveDTO.getMarkers();
        markerService.toMarker(address,count,markers);
        return Result.success(BaseConstants.SUCCESS);
    }
}

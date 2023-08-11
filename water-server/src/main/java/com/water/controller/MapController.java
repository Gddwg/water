package com.water.controller;


import com.water.dto.BaseDTO;
import com.water.dto.PointDTO;
import com.water.entity.WaterMap;
import com.water.rest.RestMap;
import com.water.result.Result;
import com.water.service.MapService;
import com.water.vo.MapListVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/map")
public class MapController {
    @Resource
    RestMap restMap;
    @Resource
    MapService mapService;
    @PostMapping("get")
    public void getMap(@RequestBody PointDTO pointDTO, HttpServletResponse res) throws IOException {
        /*String address = getMapDTO.getAddress();
        String mapName = getMapDTO.getMapName();
        Integer floor = getMapDTO.getFloor();
        Image map = restMap.getMap(address, mapName, floor);
        ImageIO.write((RenderedImage) map,"png",res.getOutputStream());*/

    }
    @PostMapping("add")
    public Result addMapPoint(@RequestBody BaseDTO baseDTO){
        mapService.addMapPoint(baseDTO.getAddress());
        return Result.success();
    }
    @PostMapping("get/point")
    public Result<WaterMap> getMapPoint(@RequestBody PointDTO pointDTO){
        WaterMap waterMap = new WaterMap();
        waterMap.setMapName(pointDTO.getMapName());
        waterMap.setFloor(pointDTO.getFloor());
        return Result.success(mapService.getMapPoint(waterMap));
    }

    @PostMapping("list")
    public Result<List<MapListVO>> getList(@RequestBody BaseDTO baseDTO){
        return Result.success(restMap.getMapList(baseDTO.getAddress()));
    }
}

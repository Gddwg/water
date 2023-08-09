package com.water.controller;


import cn.hutool.http.HttpResponse;
import com.water.dto.BaseDTO;
import com.water.dto.PointDTO;
import com.water.entity.Map;
import com.water.rest.RestMap;
import com.water.result.Result;
import com.water.service.MapService;
import com.water.vo.MapListVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.RenderedImage;
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
    public Result<Map> getMapPoint(@RequestBody PointDTO pointDTO){
        Map map = new Map();
        map.setMapName(pointDTO.getMapName());
        map.setFloor(pointDTO.getFloor());
        return Result.success(mapService.getMapPoint(map));
    }

    @PostMapping("list")
    public Result<List<MapListVO>> getList(@RequestBody BaseDTO baseDTO){
        return Result.success(restMap.getMapList(baseDTO.getAddress()));
    }
}

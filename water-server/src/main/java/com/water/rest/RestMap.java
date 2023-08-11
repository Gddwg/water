package com.water.rest;

import static com.water.constans.BaseConstans.RESULTS;
import static com.water.constans.MapConstans.*;
import com.water.constans.UrlConstans;
import com.water.entity.WaterMap;
import com.water.utils.RestUtil;
import com.water.vo.MapListVO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RestMap {
    @Resource
    RestTemplate restTemplate;
    public WaterMap getMapPoint(String address){
        String url = String.format(UrlConstans.MACHINE_MAP_POINT,address);
        Map<String, Object> res = RestUtil.get(url, restTemplate);
        Map<String, Object> results = (Map<String, Object>)res.get(RESULTS);
        String mapName = (String)results.get(MAP_NAME);
        int floor = (int) results.get(MAP_FLOOR);
        Map<String, Object> info = (Map<String, Object>)results.get(MAP_INFO);
        String resolution = String.valueOf(info.get(MAP_RESOLUTION));
        int width = (int)info.get(MAP_WIDTH);
        int height = (int)info.get(MAP_HEIGHT);
        String originX = String.valueOf(info.get(MAP_ORIGIN_X));
        String originY = String.valueOf(info.get(MAP_ORIGIN_Y));
        WaterMap waterMap = new WaterMap();
        waterMap.setMapName(mapName);
        waterMap.setFloor(floor);
        waterMap.setHeight(height);
        waterMap.setWidth(width);
        waterMap.setResolution(Double.valueOf(resolution));
        waterMap.setOriginX(Double.valueOf(originX));
        waterMap.setOriginY(Double.valueOf(originY));
        return waterMap;
    }

    public List<MapListVO> getMapList(String address){
        String url = String.format(UrlConstans.MACHINE_MAP_LIST,address);
        Map<String, Object> res = RestUtil.get(url, restTemplate);
        Map<String, Object> mapList = (Map<String, Object>)res.get(RESULTS);
        List<MapListVO> map = new ArrayList<MapListVO>();
        mapList.forEach((k, v) -> {
            String o = String.valueOf(v);
            MapListVO mapListVO = new MapListVO();
            mapListVO.setMapName(k);
            int[] floors = new int[(o.length()-1)/2];
            String[] split = o.substring(1, o.length() - 1).split(",");
            for (int i = 0; i < split.length; i++) {
                floors[i] = Integer.parseInt(split[i]);
            }
            mapListVO.setFloors(floors);
            map.add(mapListVO);
        });
        return map;
    }

    public Image getMap(String address, String mapName, int floor) {
       /* String url = String.format(UrlConstans.MACHINE_MAP,address,mapName,floor);
        Image result;
        try {
            result = restTemplate.getForObject(url, Image.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new NotResponseException(ExceptionConstans.CHANNEL_NOT_FOUND);
        }
        return result;*/
        return null;
    }
}

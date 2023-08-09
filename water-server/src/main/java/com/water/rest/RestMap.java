package com.water.rest;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.water.constans.BaseConstans;
import com.water.constans.ExceptionConstans;
import com.water.constans.MapConstans;

import static com.water.constans.BaseConstans.RESULTS;
import static com.water.constans.MapConstans.*;
import com.water.constans.UrlConstans;
import com.water.entity.Map;
import com.water.exception.NotResponseException;
import com.water.vo.MapListVO;
import net.sf.jsqlparser.expression.JsonAggregateFunction;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

@Component
public class RestMap {
    @Resource
    RestTemplate restTemplate;
    public Map getMapPoint(String address){
        String url = String.format(UrlConstans.MACHINE_MAP_POINT,address);
        java.util.Map<String, Object> res = RestUtil.get(url, restTemplate);
        java.util.Map<String, Object> results = (java.util.Map<String, Object>)res.get(RESULTS);
        String mapName = (String)results.get(MAP_NAME);
        Integer floor = (Integer) results.get(MAP_FLOOR);
        java.util.Map<String, Object> info = (java.util.Map<String, Object>)results.get(MAP_INFO);
        String resolution = String.valueOf(info.get(MAP_RESOLUTION));
        Integer width = (Integer)info.get(MAP_WIDTH);
        Integer height = (Integer)info.get(MAP_HEIGHT);
        String originX = String.valueOf(info.get(MAP_ORIGIN_X));
        String originY = String.valueOf(info.get(MAP_ORIGIN_Y));
        Map map = new Map();
        map.setMapName(mapName);
        map.setFloor(floor);
        map.setHeight(height);
        map.setWidth(width);
        map.setResolution(Double.valueOf(resolution));
        map.setOriginX(Double.valueOf(originX));
        map.setOriginY(Double.valueOf(originY));
        return map;
    }

    public List<MapListVO> getMapList(String address){
        String url = String.format(UrlConstans.MACHINE_MAP_LIST,address);
        java.util.Map<String, Object> res = RestUtil.get(url, restTemplate);
        java.util.Map<String, Object> mapList = (java.util.Map<String, Object>)res.get(RESULTS);
        List<MapListVO> map = new ArrayList<MapListVO>();
        mapList.forEach((k, v) -> {
            String o = String.valueOf(v);
            MapListVO mapListVO = new MapListVO();
            mapListVO.setMapName(k);
            Integer[] floors = new Integer[(o.length()-1)/2];
            String[] split = o.substring(1, o.length() - 1).split(",");
            for (int i = 0; i < split.length; i++) {
                floors[i] = Integer.parseInt(split[i]);
            }
            mapListVO.setFloors(floors);
            map.add(mapListVO);
        });
        return map;
    }

    public Image getMap(String address, String mapName, Integer floor) {
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

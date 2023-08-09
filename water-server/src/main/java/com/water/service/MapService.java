package com.water.service;

import com.water.entity.Map;

public interface MapService {
    Map getMapPoint(Map map);
    void addMapPoint(String address);

}

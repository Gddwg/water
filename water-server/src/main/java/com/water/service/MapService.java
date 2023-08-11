package com.water.service;

import com.water.entity.WaterMap;

public interface MapService {
    WaterMap getMapPoint(WaterMap waterMap);
    void addMapPoint(String address);

}

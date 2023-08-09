package com.water.service;

import com.water.entity.Marker;

import java.util.List;

public interface MarkerService {
    List<Marker> getMarkers(String mapName, Integer floor);
    void addMarker(String address,String mapName);

    void toMarker(String address,Integer count,List<String> markers);
}

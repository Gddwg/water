package com.water.rest;

import com.water.constans.BaseConstants;
import com.water.constans.MarkerConstants;
import com.water.constans.UrlConstants;
import com.water.entity.Marker;
import com.water.utils.RestUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RestMarker {
    @Resource
    RestTemplate restTemplate;
    public List<Marker> getMarkers(String address, String mapName){
        String url = String.format(UrlConstants.MACHINE_MARKER_POINT,address);
        Map<String, Object> result = RestUtil.get(url, restTemplate);
        Map<String, Object> res = (Map<String, Object>)result.get(BaseConstants.RESULTS);
        List<Marker> markers = new ArrayList<>();
        res.forEach((s, o) -> {
            Map<String, Object> r = (Map<String, Object>) o;
            Marker marker = new Marker();
            marker.setMarkerName((String) r.get(MarkerConstants.MARKER_NAME));
            marker.setK((Integer) r.get(MarkerConstants.MARKER_KEY));
            marker.setFloor((Integer) r.get(MarkerConstants.MARKER_FLOOR));
            Map<String, Object> pose = (Map<String, Object>)r.get(MarkerConstants.MARKER_POSE);
            Map<String, Object> position = (Map<String, Object>)pose.get(MarkerConstants.MARKER_POSITION);
            Double x = Double.parseDouble(String.valueOf(position.get(MarkerConstants.MARKER_X)));
            Double y = Double.parseDouble(String.valueOf(position.get(MarkerConstants.MARKER_Y)));
            Double z = Double.parseDouble(String.valueOf(position.get(MarkerConstants.MARKER_Z)));
            marker.setX(x);
            marker.setY(y);
            marker.setZ(z);
            Map<String, Object> orientation = (Map<String, Object>)pose.get(MarkerConstants.MARKER_ORIENTATION);
            z = Double.parseDouble(String.valueOf(orientation.get(MarkerConstants.MARKER_Z)));
            Double w = Double.parseDouble(String.valueOf(orientation.get(MarkerConstants.MARKER_W)));
            double theta = 2*Math.atan2(z,w);
            if (theta > Math.PI && theta <= (2*Math.PI)) {
                theta -= 2*Math.PI;
            } else if(theta < (-1*Math.PI) && theta >= (-2*Math.PI)) {
                theta += 2*Math.PI;
            }
            marker.setTheta(theta);
            marker.setMapName(mapName);
            markers.add(marker);
        });
        return markers;
    }

    public void toMarker(String address,Integer count,List<String> markers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < markers.size(); i++) {
            sb.append(markers.get(i));
            if (i != markers.size() - 1) {
                sb.append(',');
            }
        }
        String url = UrlConstants.MACHINE_MARKER_MOVE;
        if(markers.size() != 1){
            url = url + UrlConstants.MARKER_MOVE_MARKERS;
        }else {
            url = url + UrlConstants.MARKER_MOVE_MARKER;
        }
        if (count != null) {
            url = url + UrlConstants.MARKER_MOVE_COUNT;
        }
        url = String.format(url,address,sb,count);
        RestUtil.get(url, restTemplate);

    }
}

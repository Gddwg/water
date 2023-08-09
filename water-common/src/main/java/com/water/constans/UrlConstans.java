package com.water.constans;



public class UrlConstans {
    //params
    public static final String MARKER_MOVE_MARKERS = "?markers=%s";
    public static final String MARKER_MOVE_MARKER = "?marker=%s";
    public static final String MARKER_MOVE_COUNT = "&count=%s";
    //TCP
    public static final String LISTENING_START = "/api/request_data?topic=%s&frequency=%s";
    public static final String MACHINE_CONTROL = "/api/joy_control?angular_velocity=%s&linear_velocity=%s";

    //http
    public static final String MACHINE_STOP = "http://%s:" + LinkConstans.HTTP_PORT + "/api/move/cancel";
    public static final String MACHINE_MAP_POINT = "http://%s:" + LinkConstans.HTTP_PORT + "/api/map/get_current_map";
    public static final String MACHINE_MARKER_POINT = "http://%s:" + LinkConstans.HTTP_PORT + "/api/markers/query_list";
    public static final String MACHINE_INFO = "http://%s:" + LinkConstans.HTTP_PORT + "/api/robot_info";
    public static final String MACHINE_MAP_LIST = "http://%s:" + LinkConstans.HTTP_PORT + "/api/map/list";
    public static final String MACHINE_MAP = "http://%s:8811/%s/%s/map.png";
    public static final String MACHINE_MARKER_MOVE = "http://%s:" + LinkConstans.HTTP_PORT + "/api/move";


}

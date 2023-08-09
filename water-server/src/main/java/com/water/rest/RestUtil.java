package com.water.rest;

import com.alibaba.fastjson.JSONObject;
import com.water.constans.BaseConstans;
import com.water.constans.ExceptionConstans;
import com.water.exception.BaseException;
import com.water.exception.NotResponseException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

public class RestUtil {
    public static Map<String, Object> get(String url,RestTemplate restTemplate){
        String result;
        try {
            result = restTemplate.getForObject(url, String.class);
        }catch (Exception e){
            throw new NotResponseException(ExceptionConstans.CHANNEL_NOT_FOUND);
        }
        Map<String, Object> res = (Map<String, Object>) JSONObject.parse(result);
        String error = (String) res.get(BaseConstans.ERROR_MESSAGE);
        if(error.length() > 0){
            throw new BaseException(error);
        }
        return res;
    }
}

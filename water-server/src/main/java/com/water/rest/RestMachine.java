package com.water.rest;

import com.water.constans.BaseConstants;
import com.water.constans.MachineConstants;
import com.water.constans.UrlConstants;
import com.water.entity.Machine;
import com.water.utils.RestUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Component
public class RestMachine {
    @Resource
    RestTemplate restTemplate;
    public void stopMachine(String address) {
        String url = String.format(UrlConstants.MACHINE_STOP,address);
        RestUtil.get(url,restTemplate);
    }
    public Machine getInfo(String address, String name){
        String url = String.format(UrlConstants.MACHINE_INFO,address);
        Map<String, Object> res = RestUtil.get(url, restTemplate);
        Map<String, String> results = (Map<String, String>)res.get(BaseConstants.RESULTS);
        String productId = results.get(MachineConstants.PRODUCT_ID);
        Machine machine = new Machine();
        machine.setProductId(productId);
        machine.setAddress(address);
        machine.setCreateDate(new Date());
        machine.setName(name);
        return machine;
    }
}

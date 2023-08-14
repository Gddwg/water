package com.water.netty;

import com.alibaba.fastjson.JSONObject;
import com.water.constans.LinkConstants;
import com.water.constans.UrlConstants;
import com.water.redis.MachineRedis;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Map;

public class PushHandler {

    public static void getListening(Channel channel){

        String url = String.format(UrlConstants.LISTENING_START, "robot_status", LinkConstants.PUSH_HZ);
        byte[] req = url.getBytes();
        ByteBuf firstMessage = channel.alloc().buffer(req.length);
        firstMessage.writeBytes(req);

        channel.writeAndFlush(firstMessage);
    }
    public static void push(String address, String info){
        Map<String, String> parse = (Map<String, String>)JSONObject.parse(info);
        String topic = parse.get("topic");
        Object results = parse.get("results");
        MachineRedis.setMachinePush(address + "-" + topic,results);
    }
    public static Object getPush(String address, String topic){
        String key = address + "-" + topic;
        String status = MachineRedis.getMachinePush(key);
        return JSONObject.parse(status);
    }
}

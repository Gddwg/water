package com.water.netty;


import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelSession {

    private static Map<String, Channel> keyToChannel = new ConcurrentHashMap<String, io.netty.channel.Channel>();

    private static Map<Channel, String> channelToKey = new ConcurrentHashMap<io.netty.channel.Channel, String>();

    public static void bind(String key, io.netty.channel.Channel channel){
        unbind(key);
        keyToChannel.put(key,channel);
        channelToKey.put(channel,key);
    }

    public static void unbind(String key){
        io.netty.channel.Channel channel = keyToChannel.remove(key);
        if (channel != null) {
            channelToKey.remove(channel);
        }
    }

    public static void unbind(Channel channel) {
        String key = channelToKey.remove(channel);
        if (key != null) {
            io.netty.channel.Channel existChannel = getChannel(key);
            if (existChannel == channel) {
                keyToChannel.remove(key);
            }
        }
    }

    public static io.netty.channel.Channel getChannel(String key) {
        return keyToChannel.get(key);
    }

    public static String getKey(Channel channel) {
        return channelToKey.get(channel);
    }



}

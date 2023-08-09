package com.water;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.lettuce.core.codec.StringCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;


import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler {

    static String str;


    ClientHandler(String str){
        this.str = str;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接服务器，开始发送数据……");
        byte[] req = str.getBytes();
        ByteBuf firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        ctx.writeAndFlush(firstMessage);
    }

    //• 从服务器接收到数据后调用
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {

        System.out.println("client 读取server数据..");
        //服务端返回消息后
        ByteBuf buf = (ByteBuf) o;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        JSONObject jsonObject = JSONObject.parseObject(body);
        String formatStr = JSONObject.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        log.info(formatStr);

        //buf.release();
    }

    //• 发生异常时被调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exceptionCaught..");
        // 释放资源
        ctx.close();
    }
}

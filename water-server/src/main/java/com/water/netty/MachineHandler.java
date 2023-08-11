package com.water.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class MachineHandler extends SimpleChannelInboundHandler {

    //public static final String ROBOT_STATUS_PUSH = "/api/request_data";


    /*public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接服务器，开始发送数据……");
        byte[] req = ROBOT_STATUS_PUSH.getBytes();
        ByteBuf firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
        ctx.writeAndFlush(firstMessage);
}*/

    //• 从服务器接收到数据后调用
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {

        //System.out.println("client 读取server数据..");
        //服务端返回消息后
        ByteBuf buf = (ByteBuf) o;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        if(body.contains("callback")){
            PushHandler.push(String.valueOf(ctx.channel().remoteAddress()).split(":")[0].substring(1),body);
        }

        //JSONObject jsonObject = JSONObject.parseObject(body);
        /*String formatStr = JSONObject.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);*/
        //log.info(formatStr);
        try {
            buf.release();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //• 发生异常时被调用
    /*public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("client exceptionCaught..");
        // 释放资源
        ctx.close();
    }*/
}

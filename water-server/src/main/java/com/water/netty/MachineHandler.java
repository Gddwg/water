package com.water.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class MachineHandler extends ChannelInboundHandlerAdapter {
    /**
     * 数据接收
     * @param ctx
     * @param o
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) {


        ByteBuf buf = (ByteBuf) o;
        try {
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            String body = new String(req, StandardCharsets.UTF_8);
            if(body.contains("callback")){
                PushHandler.push(String.valueOf(ctx.channel().remoteAddress()).split(":")[0].substring(1),body);
            }
        } finally {
            buf.release();
        }

    }
}

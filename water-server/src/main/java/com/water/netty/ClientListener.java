package com.water.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class ClientListener implements ChannelFutureListener {
    private ClientConnect clientConnect;
    ClientListener(ClientConnect clientConnect){
        this.clientConnect = clientConnect;
    }
    @Override
    public void operationComplete(ChannelFuture channelFuture){
        if (!channelFuture.isSuccess()) {
            int frequency = clientConnect.getFrequency();
            if (frequency < 3) {
                channelFuture.channel().eventLoop().schedule(() -> {
                    log.info("重连服务器");
                    clientConnect.setFrequency(frequency + 1);
                    clientConnect.connect();

                }, 3000, TimeUnit.MILLISECONDS);
            }else {
                log.info("重连次数过多,通道关闭");
                clientConnect.getEventExecutors().shutdownGracefully();
            }
        } else {
            Channel channel = channelFuture.channel();

            PushHandler.getListening(channelFuture.channel());

            ChannelSession.bind(clientConnect.getAddress() + "-" + clientConnect.getPort(), channel);
            clientConnect.setFrequency(0);
            log.info("连接成功");
        }
    }

}

package com.water.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class MachineListenerTrigger extends ChannelDuplexHandler {
    private ClientConnect clientConnect;

    public MachineListenerTrigger(ClientConnect clientConnect){
        this.clientConnect = clientConnect;
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //空闲检测
        IdleStateEvent event = (IdleStateEvent) evt;
        if(event.state() == IdleState.READER_IDLE){
            //断开连接释放资源
            Channel channel = ChannelSession.getChannel(clientConnect.getAddress() + "-" + clientConnect.getPort());
            ChannelSession.unbind(channel);
            channel.close();
            clientConnect.getEventExecutors().shutdownGracefully();
            //重连
            ClientThread clientThread = new ClientThread(clientConnect.getAddress());
            clientThread.start();

        }
    }
}

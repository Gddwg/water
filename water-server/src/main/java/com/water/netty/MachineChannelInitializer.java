package com.water.netty;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;

@Data
public class MachineChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    private ClientConnect nettyClient;

    LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);

    @Override
    protected void initChannel(NioSocketChannel channel) throws Exception {

        /*MachineCodec machineCodec = new MachineCodec();
        machineCodec.setAddress(nettyClient.getAddress());
        machineCodec.setPort(nettyClient.getPort());*/

        //channel.pipeline().addLast(new MachineProtocolFrameDecoder());
        //channel.pipeline().addLast(loggingHandler);
        channel.pipeline().addLast(new MachineChannel());


    }
}

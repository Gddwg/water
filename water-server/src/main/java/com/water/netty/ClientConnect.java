package com.water.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
public class ClientConnect {
    private NioEventLoopGroup eventExecutors;
    private MachineChannelInitializer machineChannelInitializer;
    private Bootstrap bootstrap;

    private String address;

    private int port;
    private int frequency = 0;



    public void init(){
        eventExecutors = new NioEventLoopGroup();
        machineChannelInitializer = new MachineChannelInitializer();
        machineChannelInitializer.setClientConnect(this);
        //创建bootstrap对象，配置参数
        bootstrap = new Bootstrap();
        //设置线程组
        bootstrap.group(eventExecutors)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                //设置客户端的通道实现类型
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                //使用匿名内部类初始化通道
                .handler(machineChannelInitializer);
        log.info("客户端启动ip:{},port:{}",address,port);
        connect();

    }
    public void connect() {
        try {
            ChannelFuture channelFuture = bootstrap.connect(address, port);
            //添加监听进行重连
            channelFuture.addListener(new ClientListener(this));
            //对通道关闭进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

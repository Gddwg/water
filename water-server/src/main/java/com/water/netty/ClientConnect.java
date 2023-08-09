package com.water.netty;

import com.water.service.MachineService;
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

    private String address;

    private int port;



    public void connet(){
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        MachineChannelInitializer machineChannelInitializer = new MachineChannelInitializer();
        machineChannelInitializer.setNettyClient(this);

        try {
            //创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            //设置线程组
            bootstrap.group(eventExecutors)
                    //设置客户端的通道实现类型
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    //使用匿名内部类初始化通道
                    .handler(machineChannelInitializer);
            log.info("客户端启动ip:{},port:{}",address,port);
            //连接服务端
            ChannelFuture channelFuture = bootstrap.connect(address, port).sync();
            //获取推送
            PushHandler.getListening(channelFuture.channel());

            Channel channel = channelFuture.sync().channel();

            ChannelSession.bind(address + "-" + port, channel);

            //对通道关闭进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //关闭线程组
            eventExecutors.shutdownGracefully();
        }
    }
}

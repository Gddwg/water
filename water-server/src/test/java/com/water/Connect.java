package com.water;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Scanner;

public class Connect {
    Connect(){
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        Scanner sc = new Scanner(System.in);
        String info = sc.next();
        try {
            //创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            //设置线程组
            bootstrap.group(eventExecutors)
                    //设置客户端的通道实现类型
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    //使用匿名内部类初始化通道
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline().addLast(loggingHandler);
                            //nioSocketChannel.pipeline().addLast(new MachineProtocolFrameDecoder());
                            nioSocketChannel.pipeline().addLast(new ClientHandler(info));
                        }
                    });
            //连接服务端
            ChannelFuture channelFuture = bootstrap.connect("192.168.1.3", 31001).sync();

            //Channel channel = channelFuture.sync().channel();

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

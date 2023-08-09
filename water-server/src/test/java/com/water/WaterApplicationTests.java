package com.water;

import com.water.entity.Machine;
import com.water.entity.Map;
import com.water.entity.Marker;
import com.water.netty.ClientThread;
import com.water.rest.RestMachine;
import com.water.rest.RestMap;
import com.water.rest.RestMarker;
import com.water.service.MarkerService;
import com.water.vo.MapListVO;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;


import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class WaterApplicationTests {
    @Resource
    RestMap restMap;
    @Resource
    RestTemplate restTemplate;
    @Resource
    RestMachine restMachine;
    @Resource
    RestMarker restMarker;
    @Resource
    MarkerService markerService;


    @Test
    void contextLoads() {
        connect();
    }

    @Test
    void test(){
        List<Marker> hg = markerService.getMarkers("hg", 5);
        System.out.println(hg);
    }






    static void connect(){
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);

        try {
            //创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            //设置线程组
            bootstrap.group(eventExecutors)
                    //设置客户端的通道实现类型
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    //使用匿名内部类初始化通道
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline().addLast(loggingHandler);
                            nioSocketChannel.pipeline().addLast(new ClientHandler("/api/request_data?topic=robot_status&frequency=1"));
                        }
                    });
            //连接服务端
            ChannelFuture channelFuture = bootstrap.connect("192.168.1.3",31001).sync();

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

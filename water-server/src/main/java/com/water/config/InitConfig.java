package com.water.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water.entity.Machine;
import com.water.mapper.MachineMapper;
import com.water.netty.ChannelSession;
import com.water.netty.ClientConnect;
import com.water.netty.ClientThread;
import com.water.utils.RedisUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

@Order(1)
@Component
public class InitConfig implements ApplicationRunner {

    @Resource
    MachineMapper machineMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new RedisUtil(stringRedisTemplate);

        //初始化连接
        List<Machine> machines = machineMapper.selectList(new QueryWrapper<>());
        machines.forEach(machine -> {
            ClientThread clientThread = new ClientThread(machine.getAddress());
            clientThread.start();
        });
    }
}

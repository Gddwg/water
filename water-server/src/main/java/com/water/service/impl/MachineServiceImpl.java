package com.water.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.water.constans.ExceptionConstants;
import com.water.constans.LinkConstants;
import com.water.constans.UrlConstants;
import com.water.dto.ControlDTO;
import com.water.entity.Machine;
import com.water.exception.ChannelNotFoundException;
import com.water.mapper.MachineMapper;
import com.water.netty.ChannelSession;
import com.water.rest.RestMachine;
import com.water.service.MachineService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MachineServiceImpl implements MachineService {
    @Resource
    MachineMapper machineMapper;
    @Resource
    RestMachine restMachine;

    @Override
    public List<Machine> getMachines() {
        return machineMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public void add(String address, String name) {
        Machine info = restMachine.getInfo(address, name);
        machineMapper.insert(info);
    }

    @Override
    public void control(ControlDTO controlDTO){
        Channel channel = ChannelSession.getChannel(controlDTO.getAddress() + "-" + LinkConstants.TCP_PORT);
        if (channel == null) {
            throw new ChannelNotFoundException(ExceptionConstants.CHANNEL_NOT_FOUND);
        }
        String url = String.format(UrlConstants.MACHINE_CONTROL,controlDTO.getAngularVelocity(),controlDTO.getLinearVelocity());
        byte[] req = url.getBytes();
        ByteBuf firstMessage = channel.alloc().buffer(req.length);
        firstMessage.writeBytes(req);
        channel.writeAndFlush(firstMessage);
    }

    @Override
    public void stop(String address){
        restMachine.stopMachine(address);
    }
}

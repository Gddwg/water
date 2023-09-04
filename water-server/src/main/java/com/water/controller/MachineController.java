package com.water.controller;

import com.water.constans.*;
import com.water.dto.AddMachineDTO;
import com.water.dto.BaseDTO;
import com.water.dto.ControlDTO;
import com.water.entity.Machine;
import com.water.exception.ChannelNotFoundException;
import com.water.exception.NotResponseException;
import com.water.netty.ClientThread;
import com.water.netty.PushHandler;
import com.water.result.Result;
import com.water.service.MachineService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("api/machine")
public class MachineController {

    @Resource
    MachineService machineService;

    /**
     * 获取所有机器人
     * @return
     */
    @GetMapping("get")
    public Result<List<Machine>> getMachines(){
        List<Machine> machines = machineService.getMachines();
        return Result.success(machines);
    }

    /**
     * 添加新的机器人
     * @param addMachineDTO
     * @return
     */
    @PostMapping("add")
    public Result<String> addMachine(@RequestBody AddMachineDTO addMachineDTO){
        String address = addMachineDTO.getAddress();
        String name = addMachineDTO.getName();
        if(address == null || name == null){
            return Result.error(BaseConstants.ERROR);
        }
        machineService.add(address,name);

        ClientThread clientThread = new ClientThread(address);
        clientThread.start();

        return Result.success(BaseConstants.SUCCESS);
    }

    /*public Result<String> start(String topic,String frequency) {
        Channel channel = ChannelSession.getChannel("192.168.1.3-31001");
        if (channel == null) {
            System.out.println("channel : null");
        }
        String url = String.format(UrlConstans.LISTENING_START, topic, frequency);
        byte[] req = url.getBytes();
        ByteBuf firstMessage = Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);

        channel.writeAndFlush(firstMessage);

        return Result.success("listening_success");
    }*/

    /**
     * 获取机器人实时状态
     * @param baseDTO
     * @return
     */
    @GetMapping("status")
    public Result<Object> getStatus(BaseDTO baseDTO){
        Object push = PushHandler.getPush(baseDTO.getAddress(), MachineConstants.STATUS);
        if (push == null){
            throw new NotResponseException(ExceptionConstants.CHANNEL_NOT_FOUND);
        }
        return Result.success(push);
    }

    /**
     * 直接控制机器人
     * @param controlDTO
     * @throws ChannelNotFoundException
     */
    @PostMapping("control")
    public void control(@RequestBody ControlDTO controlDTO) throws ChannelNotFoundException {
        machineService.control(controlDTO);
    }

    /**
     * 机器人停止移动
     * @param baseDTO
     */
    @GetMapping("stop")
    public void stop(BaseDTO baseDTO){
        machineService.stop(baseDTO.getAddress());
    }
}

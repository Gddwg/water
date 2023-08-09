package com.water.service;

import com.water.dto.ControlDTO;
import com.water.entity.Machine;
import com.water.exception.ChannelNotFoundException;
import com.water.exception.NotResponseException;

import java.util.List;

public interface MachineService {

    List<Machine> getMachines();
    void add(String address, String name);
    void control(ControlDTO controlDTO);

    void stop(String address);


}

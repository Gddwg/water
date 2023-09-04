package com.water.netty;

import com.water.constans.LinkConstants;
import lombok.Data;

@Data
public class ClientThread extends Thread{

    String address;

    public ClientThread(String address){
        this.address = address;
    }

    /**
     * 连接
     */
    @Override
    public void run(){
        ClientConnect clientConnect = new ClientConnect();
        clientConnect.setAddress(address);
        clientConnect.setPort(LinkConstants.TCP_PORT);
        clientConnect.init();
    }
}

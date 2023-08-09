package com.water.netty;

import com.water.constans.LinkConstans;
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
        clientConnect.setPort(LinkConstans.TCP_PORT);
        clientConnect.connet();
    }
}

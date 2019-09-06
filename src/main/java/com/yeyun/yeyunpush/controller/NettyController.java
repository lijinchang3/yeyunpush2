package com.yeyun.yeyunpush.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.yeyun.yeyunpush.message.MessageEventHandler;
import com.yeyun.yeyunpush.message.MessageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * todo 加点注释
 *
 * @author: zhoujw
 * @Date: 2018-08-02
 */
@Controller
public class NettyController {

    @GetMapping("/pushArr")
    @ResponseBody
    public void sendSocMsg() throws InterruptedException {
        System.out.println("给全体人员发送消息");
        SocketIOServer server=MessageEventHandler.server;
        for (SocketIOClient clientSocket: server.getAllClients()) {
            /*
               todo 客户端支持操作
                void send(Packet var1);
                void disconnect();
                void sendEvent(String var1, Object... var2);
             */
            MessageInfo sendData = new MessageInfo();
            sendData.setSourceClientId("系统发送");
            sendData.setTargetClientId("系统目的地");
            sendData.setMsgType("chat");
            sendData.setMsgContent("Hello World!!");
            clientSocket.sendEvent("messageevent",sendData);
        }

    }
}

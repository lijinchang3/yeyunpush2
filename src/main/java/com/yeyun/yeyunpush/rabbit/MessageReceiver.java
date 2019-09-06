package com.yeyun.yeyunpush.rabbit;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.rabbitmq.tools.json.JSONUtil;
import com.yeyun.yeyunpush.message.MessageEventHandler;
import com.yeyun.yeyunpush.message.MessageInfo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

@RabbitListener(queues = "helloQueue")

public class MessageReceiver {
    @RabbitHandler
    public void process(Object hello) throws JsonProcessingException {

        System.out.println("Receiver1  : " + hello);
        SocketIOServer server= MessageEventHandler.server;
        for (SocketIOClient clientSocket: server.getAllClients()) {
            /*
               todo 客户端支持操作
                void send(Packet var1);
                void disconnect();
                void sendEvent(String var1, Object... var2);
             */
            MessageInfo sendData = new MessageInfo();
            sendData.setSourceClientId("rabbitmq发送");
            sendData.setTargetClientId("系统目的地");
            sendData.setMsgType("chat");
            sendData.setMsgContent("大家好");
            ObjectMapper mapper = new ObjectMapper();
            String obj= mapper.writeValueAsString(hello);
            sendData.setContent(obj);
            clientSocket.sendEvent("messageevent",sendData);
        }
    }

}
package com.yeyun.yeyunpush.message;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSON;
import com.yeyun.yeyunpush.entity.ClientInfo;
import com.yeyun.yeyunpush.repository.ClientInfoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
@Component
public class MessageEventHandler
{
    public static SocketIOServer server;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @Autowired
    public MessageEventHandler(SocketIOServer server)
    {
        this.server = server;
    }
    //添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
    //方便后面发送消息时查找到对应的目标client,
    @OnConnect
    public void onConnect(SocketIOClient client)
    {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        ClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
        if (clientInfo != null)
        {
            Date nowTime = new Date(System.currentTimeMillis());
            clientInfo.setConnected((short)1);
            String uuid=client.getSessionId().toString();
            System.out.println("发送者"+clientId+"的SessionID："+uuid);
            clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
            clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
            clientInfo.setLastconnecteddate(nowTime);
            clientInfoRepository.save(clientInfo);
        }
    }

    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    public void onDisconnect(SocketIOClient client)
    {
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        ClientInfo clientInfo = clientInfoRepository.findClientByclientid(clientId);
        if (clientInfo != null)
        {
            clientInfo.setConnected((short)0);
            clientInfo.setMostsignbits(null);
            clientInfo.setLeastsignbits(null);
            clientInfoRepository.save(clientInfo);
        }
    }

    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data)
    {
        String targetClientId = data.getTargetClientId();
        //String appid = client.getHandshakeData().getSingleUrlParam("appid") ;
        ClientInfo clientInfo = clientInfoRepository.findClientByclientid(targetClientId);
        if (clientInfo != null && clientInfo.getConnected() != 0)
        {
            UUID uuid = new UUID(clientInfo.getMostsignbits(), clientInfo.getLeastsignbits());
            System.out.println("发送者"+data.getSourceClientId()+"的SessionID："+client.getSessionId().toString());
            System.out.println("目标"+targetClientId+"的SessionID："+uuid.toString());
            MessageInfo sendData = new MessageInfo();
            sendData.setSourceClientId(data.getSourceClientId());
            sendData.setTargetClientId(data.getTargetClientId());
            sendData.setMsgType("chat");
            String value=JSON.toJSONString(data.getMsgContent());
            sendData.setMsgContent(value);
            server.getClient(uuid).sendEvent("messageevent", sendData);
        }

    }


}
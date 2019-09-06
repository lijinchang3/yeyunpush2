//package com.yeyun.yeyunpush.listenner;
//
//import com.corundumstudio.socketio.AuthorizationListener;
//import com.corundumstudio.socketio.Configuration;
//import com.corundumstudio.socketio.HandshakeData;
//import com.corundumstudio.socketio.SocketIOServer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//
///**
// * 监听器启动
// *
// * @author: zhoujw
// * @Date: 2018-08-02
// */
//@WebListener
//public class SocketListener implements ServletContextListener {
//
//    private final SocketIOServer server;
//    @Value("${wss.server.host}")
//    private String host;
//
//    @Value("${wss.server.port}")
//    private Integer port;
//
//    @Autowired
//    public SocketListener(SocketIOServer server) {
//        this.server = server;
//    }
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        if(null==server) {
//            //启动监听服务
//            Configuration config = new Configuration();
//            config.setHostname(host);
//            config.setPort(port);
//
//            //该处可以用来进行身份验证
//            config.setAuthorizationListener(new AuthorizationListener() {
//                @Override
//                public boolean isAuthorized(HandshakeData data) {
//                    //http://localhost:8081?username=test&password=test
//                    //例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证
////				String username = data.getSingleUrlParam("username");
////				String password = data.getSingleUrlParam("password");
//                    return true;
//                }
//            });
//
//            final SocketIOServer server = new SocketIOServer(config);
//            server.start();
//
//            //设置超时时间
//            try {
//                Thread.sleep(Integer.MAX_VALUE);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            server.stop();
//        }
//        else
//            server.start();
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//        //关闭Socketio服务
//        if (server != null) {
//            server.stop();
//            //server = null;
//        }
//    }
//
//
//}
//

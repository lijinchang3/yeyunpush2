package com.yeyun.yeyunpush.controller;

import com.yeyun.yeyunpush.entity.UserInfo;
import com.yeyun.yeyunpush.rabbit.MessageSender;
import com.yeyun.yeyunpush.service.clientinfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import  com.yeyun.yeyunpush.entity.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * todo 加点注释
 *
 * @author: zhoujw
 * @Date: 2018-08-02
 */

@Controller
public class IndexController {

    @Autowired
    private MessageSender messageSender;
    @Autowired
    private com.yeyun.yeyunpush.service.clientinfosService clientinfosService;

    @RequestMapping("/hello")
    @ResponseBody
    public void hello(HttpServletRequest request) {
        String param=request.getParameter("query");
        UserInfo user=new UserInfo();
        user.setId(200);
        user.setName(param);
        messageSender.send(param);
    }
    @RequestMapping("/adduser")
    @ResponseBody
    public void adduser()
    {
        clientinfos clientinfos1=new clientinfos();
        clientinfos1.setClientid("testclient3");
        clientinfos1.setConnected((short)0);
        clientinfosService.insert(clientinfos1);
    }
    @RequestMapping("/updateuser/{clientid}")
    @ResponseBody
    public void updateuser(@PathVariable("clientid") @NotNull String clientid)
    {
        clientinfos clientinfos1=clientinfosService.queryByID(clientid);
        clientinfos1.setConnected((short)0);
        clientinfos1.setLastconnecteddate(new Date());
        clientinfosService.updateClient(clientinfos1);
    }
    @RequestMapping("/finduser/{clientid}")
    @ResponseBody
    public List<clientinfos> finduser(@PathVariable("clientid") @NotNull String clientid)
    {
        clientinfos clientinfos1=clientinfosService.queryByID(clientid);

        return clientinfosService.findClientInfos(clientinfos1);
    }
    @InitBinder
    private void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    @GetMapping("/get/{date}")
    @ResponseBody
    public Object get(@PathVariable("date") Date date) throws ParseException {
        Map<String ,Object> result = new HashMap<>(16);
        result.put("name", "james");
        result.put("msg","ok");
        result.put("date", date);
        return result;
    }


    @GetMapping("/")
    public String getIndex(){
        return "index";
    }
    @GetMapping("/index1")
    public String getIndex1(){
        return "index1";
    }
    @GetMapping("/netty")
    public String getNetty(){
        return "netty";
    }
    @GetMapping("test")
    public  String getTest()
    {
        return  "test";
    }

}

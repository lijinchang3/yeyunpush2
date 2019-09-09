package com.yeyun.yeyunpush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppManageController {
    @GetMapping("/AppManage")
    public  String AppManage()
    {
        return  "AppManage";
    }
}

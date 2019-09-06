package com.yeyun.yeyunpush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
@Controller
public class LoginController {
    @RequestMapping("/login")
    public String login(HttpServletRequest request)
    {
        return "login";
    }
}

package com.yeyun.yeyunpush.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * todo 加点注释
 *
 * @author: zhoujw
 * @Date: 2018-08-02
 */

@Controller
public class IndexController {



    @GetMapping("/index")
    public String getIndex(){
        return "index";
    }


}

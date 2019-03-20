package com.rookiefly.springboot.helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *  登录页面
 */
@Controller
public class IndexController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

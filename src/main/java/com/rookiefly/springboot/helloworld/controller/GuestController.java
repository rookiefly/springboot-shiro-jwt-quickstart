package com.rookiefly.springboot.helloworld.controller;

import com.rookiefly.springboot.helloworld.model.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  游客角色可以访问的页面
 */
@RestController
@RequestMapping("/guest")
public class GuestController {

    @GetMapping("/welcome")
    public ResultMap welcome() {
        ResultMap resultMap = new ResultMap();
        return resultMap.success().code(200).message("欢迎访问游客页面！");
    }
}

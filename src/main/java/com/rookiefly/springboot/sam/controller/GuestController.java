package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 游客角色可以访问的页面
 */
@RestController
@RequestMapping("/guest")
public class GuestController {

    @GetMapping("/welcome")
    public Result welcome() {
        return Result.success("欢迎访问游客页面！");
    }
}

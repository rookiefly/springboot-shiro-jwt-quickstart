package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.common.Result;
import com.rookiefly.springboot.sam.mapper.rbac.UserMapper;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserMapper userMapper;

    @Autowired
    public AdminController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("/getUser")
    @RequiresRoles("admin")
    public Result getUser() {
        List<String> list = userMapper.getUser();
        return Result.success(list);
    }

    /**
     * 封号操作
     */
    @PostMapping("/banUser")
    @RequiresRoles("admin")
    public Result updatePassword(String username) {
        userMapper.banUser(username);
        return Result.success("OK");
    }
}

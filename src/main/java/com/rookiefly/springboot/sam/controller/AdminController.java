package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.mapper.rbac.UserMapper;
import com.rookiefly.springboot.sam.model.ResultMap;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  admin角色权限controller
 */
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
    public ResultMap getUser() {
        List<String> list = userMapper.getUser();
        ResultMap resultMap = new ResultMap();
        return resultMap.success().code(200).message(list);
    }

    /**
     * 封号操作
     */
    @PostMapping("/banUser")
    @RequiresRoles("admin")
    public ResultMap updatePassword(String username) {
        userMapper.banUser(username);
        ResultMap resultMap = new ResultMap();
        return resultMap.success().code(200).message("成功封号！");
    }
}

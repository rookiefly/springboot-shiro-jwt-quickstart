package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.common.Result;
import com.rookiefly.springboot.sam.mapper.rbac.UserMapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper userMapper;

    @Autowired
    public UserController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/updatePassword")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    public Result updatePassword(String username, String oldPassword, String newPassword) {
        String dataBasePassword = userMapper.getPassword(username);
        if (dataBasePassword.equals(oldPassword)) {
            userMapper.updatePassword(username, newPassword, oldPassword);
        } else {
            return Result.fail("密码错误！");
        }
        return Result.success("OK");
    }

    @GetMapping("/getVipMessage")
    @RequiresRoles(logical = Logical.OR, value = {"user", "admin"})
    @RequiresPermissions("vip")
    public Result getVipMessage() {
        return Result.success("成功获得 vip 信息！");
    }

    @GetMapping("/list")
    public Result list() {
        return Result.success("/user/list");
    }

    @GetMapping("/view")
    public Result view() {
        return Result.success("/user/view");
    }

    @GetMapping("/add")
    public Result add() {
        return Result.success("/user/add");
    }

    @GetMapping("/edit")
    public Result edit() {
        return Result.success("/user/edit");
    }

    @GetMapping("/delete")
    public Result delete() {
        return Result.success("/user/delete");
    }
}

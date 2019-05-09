package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.mapper.rbac.UserMapper;
import com.rookiefly.springboot.sam.model.ResultMap;
import com.rookiefly.springboot.sam.model.rbac.User;
import com.rookiefly.springboot.sam.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录
 */
@RestController
public class LoginController {

    private final UserMapper userMapper;

    @Autowired
    public LoginController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResultMap login(@RequestBody User user) {
        String realPassword = userMapper.getPassword(user.getUserName());
        ResultMap resultMap = new ResultMap();
        if (realPassword == null) {
            return resultMap.fail().code(401).message("用户名错误");
        } else if (!realPassword.equals(user.getPassword())) {
            return resultMap.fail().code(401).message("密码错误");
        } else {
            return resultMap.success().code(200).message(JWTUtil.createToken(user.getUserName()));
        }
    }

    @RequestMapping(path = "/unauthorized/{message}")
    public ResultMap unauthorized(@PathVariable String message) {
        ResultMap resultMap = new ResultMap();
        return resultMap.success().code(401).message(message);
    }
}

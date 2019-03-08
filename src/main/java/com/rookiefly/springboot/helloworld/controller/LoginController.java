package com.rookiefly.springboot.helloworld.controller;

import com.rookiefly.springboot.helloworld.mapper.UserMapper;
import com.rookiefly.springboot.helloworld.model.ResultMap;
import com.rookiefly.springboot.helloworld.model.User;
import com.rookiefly.springboot.helloworld.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    private final UserMapper userMapper;
    private final ResultMap resultMap;

    @Autowired
    public LoginController(UserMapper userMapper, ResultMap resultMap) {
        this.userMapper = userMapper;
        this.resultMap = resultMap;
    }

    @PostMapping("/login")
    public ResultMap login(@RequestBody User user) {
        String realPassword = userMapper.getPassword(user.getUserName());
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
        return resultMap.success().code(401).message(message);
    }
}

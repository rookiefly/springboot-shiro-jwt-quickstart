package com.rookiefly.springboot.sam.service.impl;

import com.rookiefly.springboot.sam.mapper.rbac.UserMapper;
import com.rookiefly.springboot.sam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(value = "rbacCache", key = "targetClass + methodName + #username")
    public String queryPasswordByUserName(String username) {
        return userMapper.getPassword(username);
    }
}

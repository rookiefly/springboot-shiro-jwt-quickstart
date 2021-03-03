package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.common.Result;
import com.rookiefly.springboot.sam.service.UserService;
import com.rookiefly.springboot.sam.shiro.JwtRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/role/permission")
public class RolePermissionController {

    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;
    @Autowired
    JwtRealm jwtRealm;

    @Autowired
    UserService userService;

    @GetMapping("/edit")
    public Result edit() {
        /**
         * 模拟修改角色与权限，将权限rolePermisssionEdit赋值给角色cudrRole，
         * 前端发一个请求给此接口后，cudrOtherUser就能访问/role/permission/edit接口了
         */
        userService.cudrRoleAddRolePermisssionEdit();

        /**
         * 清除jwtRealm的授权缓存
         * 缓存清除后，用户访问设置了权限的url，那么com.example.shirojwt.JwtRealm#doGetAuthorizationInfo()会被再次调用，重新设置用户权限
         */
        jwtRealm.getAuthorizationCache().clear();
        return Result.success("/role/permission/edit");
    }
}
package com.rookiefly.springboot.sam.service.impl;

import com.rookiefly.springboot.sam.mapper.rbac.UserMapper;
import com.rookiefly.springboot.sam.model.rbac.User;
import com.rookiefly.springboot.sam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.rookiefly.springboot.sam.common.Constant.ADMIN;
import static com.rookiefly.springboot.sam.common.Constant.CUDR_OTHER_USER;
import static com.rookiefly.springboot.sam.common.Constant.CUDR_ROLE;
import static com.rookiefly.springboot.sam.common.Constant.ROLE_PERMISSSION_EDIT;
import static com.rookiefly.springboot.sam.common.Constant.VIEW_USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 通过username获取用户，
     * 实际项目中使用数据库中的数据，类似 userService.getUser()
     */
    @Override
    @Cacheable(value = "rbacCache", key = "targetClass + methodName + #username")
    public User getUser(String username) {
        if ("admin".equals(username)) {
            return ADMIN;
        } else if ("cudrOtherUser".equals(username)) {
            return CUDR_OTHER_USER;
        } else if ("viewUser".equals(username)) {
            return VIEW_USER;
        }
        return null;
    }

    /**
     * 将rolePermisssionEdit权限添加到cudrRole
     */
    @Override
    public void cudrRoleAddRolePermisssionEdit() {
        CUDR_ROLE.getPermissionList().add(ROLE_PERMISSSION_EDIT);
    }
}

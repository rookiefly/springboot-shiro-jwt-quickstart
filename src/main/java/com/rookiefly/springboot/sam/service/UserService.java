package com.rookiefly.springboot.sam.service;

import com.rookiefly.springboot.sam.model.rbac.User;

public interface UserService {

    void cudrRoleAddRolePermisssionEdit();

    User getUser(String username);
}

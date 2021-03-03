package com.rookiefly.springboot.sam.model.rbac;

import lombok.Data;

import java.util.List;

@Data
public class User {

    private Integer userId;

    private String username;

    private String password;

    private List<Role> roleList;

    /**
     * 用户状态，1-禁用 0-正常
     */
    private Integer status;

    public User() {
    }

    public User(String username, String password, List<Role> roleList) {
        this.username = username;
        this.password = password;
        this.roleList = roleList;
    }
}

package com.rookiefly.springboot.sam.vo;

import com.rookiefly.springboot.sam.model.rbac.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserVO {

    private Integer userId;

    private String username;

    private String password;

    /**
     * 用户状态，1-禁用 0-正常
     */
    private Integer status;

    private List<Role> roleList;

}

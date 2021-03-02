package com.rookiefly.springboot.sam.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserVO {

    private Integer id;

    private String userName;

    private String password;

    public UserVO(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}

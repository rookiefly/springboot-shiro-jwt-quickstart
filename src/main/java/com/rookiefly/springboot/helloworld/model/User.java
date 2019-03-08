package com.rookiefly.springboot.helloworld.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by hzwanggaoxiang on 2017/7/13.
 */
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    private Integer id;

    private String userName;

    private String password;

    public User(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }
}

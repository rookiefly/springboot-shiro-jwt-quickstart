package com.rookiefly.springboot.sam.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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

package com.rookiefly.springboot.sam.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {

    private Integer userId;

    private String username;

    private String password;
}

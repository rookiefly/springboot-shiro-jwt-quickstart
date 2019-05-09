package com.rookiefly.springboot.sam.mapper.rbac;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 获得密码
     *
     * @param username 用户名
     */
    String getPassword(String username);

    /**
     * 修改密码
     */
    void updatePassword(String username, String newPassword);

    /**
     * 获得存在的用户
     */
    List<String> getUser();

    /**
     * 封号
     */
    void banUser(String username);

    /**
     * 检查用户状态
     */
    int checkUserBanStatus(String username);
}

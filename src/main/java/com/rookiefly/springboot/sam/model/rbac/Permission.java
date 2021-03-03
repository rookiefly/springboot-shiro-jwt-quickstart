package com.rookiefly.springboot.sam.model.rbac;

import lombok.Data;

/**
 * @author chenpiqian
 * @date: 2020-01-15
 */
@Data
public class Permission {

    private String name;

    public Permission() {
    }

    public Permission(String name) {
        this.name = name;
    }
}

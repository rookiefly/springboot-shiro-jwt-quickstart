package com.rookiefly.springboot.sam.common;

import com.rookiefly.springboot.sam.model.rbac.User;

public class UserThreadLocal {

    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void set(User user) {
        threadLocal.set(user);
    }

    public static User get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
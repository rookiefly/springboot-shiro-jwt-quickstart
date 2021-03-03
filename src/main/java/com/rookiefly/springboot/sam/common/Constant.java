package com.rookiefly.springboot.sam.common;

import com.rookiefly.springboot.sam.model.rbac.Permission;
import com.rookiefly.springboot.sam.model.rbac.Role;
import com.rookiefly.springboot.sam.model.rbac.User;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.ArrayList;
import java.util.Arrays;

public class Constant {

    public static final Integer CODE_SUCCESS = 1;
    public static final Integer CODE_FAIL = 0;

    /**
     * token失效，前端接收到这个值后，就给出提示信息并跳转到登录页
     */
    public static final Integer CODE_TOKEN_ERROR = 12345;

    public static final String CREDENTIALS_EMPTY = "";

    /**
     * 过期时间
     */
    public static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;

    /**
     * 权限
     */
    public static final Permission USER_VIEW = new Permission("user/view");
    public static final Permission USER_ADD = new Permission("user/add");
    public static final Permission USER_EDIT = new Permission("user/edit");
    public static final Permission USER_DELETE = new Permission("user/delete");
    public static final Permission TEST_OTHER = new Permission("test/other");
    public static final Permission ROLE_PERMISSSION_EDIT = new Permission("role/permission/edit");

    /**
     * 新建角色并设置角色权限
     */
    public static final Role VIEW_ROLE = new Role("userView", new ArrayList<Permission>() {{
        add(USER_VIEW);
    }});

    public static final Role CUDR_ROLE = new Role("cudr", new ArrayList<Permission>() {{
        add(USER_VIEW);
        add(USER_ADD);
        add(USER_EDIT);
        add(USER_DELETE);
    }});

    public static final Role OTHER_ROLE = new Role("testOther", new ArrayList<Permission>() {{
        add(TEST_OTHER);
    }});

    /**
     * adminRole关联的权限太多，可在数据库中给adminRole一个特殊标识，表示此角色关联所有权限，写SQL查询时通过特殊标识查出所有权限
     */
    public static final Role ADMIN_ROLE = new Role("admin", new ArrayList<Permission>() {{
        add(USER_VIEW);
        add(USER_ADD);
        add(USER_EDIT);
        add(USER_DELETE);
        add(TEST_OTHER);
        add(ROLE_PERMISSSION_EDIT);
    }});

    /**
     * 新建用户并设置用户角色
     */
    public static final User ADMIN = new User("admin",
            new Md5Hash("admin-pwd").toString(), Arrays.asList(ADMIN_ROLE));

    public static final User CUDR_OTHER_USER = new User("cudrOtherUser",
            new Md5Hash("cudrOtherUser-pwd").toString(), new ArrayList<Role>() {{
        add(CUDR_ROLE);
        add(OTHER_ROLE);
    }});

    public static final User VIEW_USER = new User("viewUser",
            new Md5Hash("viewUser-pwd").toString(), Arrays.asList(VIEW_ROLE));

}

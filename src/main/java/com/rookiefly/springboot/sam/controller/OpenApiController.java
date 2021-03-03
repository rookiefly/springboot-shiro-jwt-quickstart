package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.common.Result;
import com.rookiefly.springboot.sam.dto.UserDTO;
import com.rookiefly.springboot.sam.exception.CustomException;
import com.rookiefly.springboot.sam.model.rbac.User;
import com.rookiefly.springboot.sam.service.UserService;
import com.rookiefly.springboot.sam.shiro.JwtRealm;
import com.rookiefly.springboot.sam.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.rookiefly.springboot.sam.common.Constant.ROLE_PERMISSSION_EDIT;
import static com.rookiefly.springboot.sam.common.Constant.TEST_OTHER;
import static com.rookiefly.springboot.sam.common.Constant.USER_ADD;
import static com.rookiefly.springboot.sam.common.Constant.USER_DELETE;
import static com.rookiefly.springboot.sam.common.Constant.USER_EDIT;
import static com.rookiefly.springboot.sam.common.Constant.USER_VIEW;

@RestController
@Slf4j
@RequestMapping("/open")
public class OpenApiController {

    @Autowired
    ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    JwtRealm jwtRealm;

    @Autowired
    UserService userService;

    /**
     * 登陆
     * 开放接口，不使用shiro拦截，生成令牌并返回给前端
     * 用户名             密码
     * admin           admin-pwd
     * cudrOtherUser   cudrOtherUser-pwd
     * viewUser        viewUser-pwd
     */
    @PostMapping("/login")
    public Result openLogin(@RequestBody UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        // 用户名密码校验
        User user = userService.getUser(username);
        if (user == null) {
            throw new CustomException("无此用户");
        }
        if (!user.getPassword().equals(new Md5Hash(password).toString())) {
            throw new CustomException("用户名或密码错误");
        }

        // 生成令牌
        String token = JWTUtil.sign(username, user.getPassword());

        /**
         * 在登陆接口中就执行shiro用户认证，用于测试不禁用session存储的情形
         */
        //JwtToken jwtToken = new JwtToken(token);
        //Subject subject = SecurityUtils.getSubject();
        //subject.login(jwtToken);

        return Result.success(token);
    }

    /**
     * 测试动态修改接口授权配置
     */
    @GetMapping("/definition/test")
    public Result test1() throws Exception {

        AbstractShiroFilter shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();
        // 清空老的权限控制
        manager.getFilterChains().clear();
        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();

        // 生成新的definitionMap
        LinkedHashMap<String, String> definitionMap = new LinkedHashMap<>();
        definitionMap.put("/open/**", "anon");

        definitionMap.put("/user/add", "jwt, customPerms[" + USER_ADD.getName() + "]");
        definitionMap.put("/user/delete", "jwt, customPerms[" + USER_DELETE.getName() + "]");
        definitionMap.put("/user/edit", "jwt, customPerms[" + USER_EDIT.getName() + "]");
        definitionMap.put("/user/view", "jwt, customPerms[" + USER_VIEW.getName() + "]");
        definitionMap.put("/test/other", "jwt, customPerms[" + TEST_OTHER.getName() + "]");
        definitionMap.put("/role/permission/edit", "jwt, customPerms[" + ROLE_PERMISSSION_EDIT.getName() + "]");

        definitionMap.put("/**", "jwt");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(definitionMap);

        // 重新构建生成权限过滤链
        for (Map.Entry<String, String> entry : definitionMap.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue();
            manager.createChain(url, chainDefinition);
        }

        return Result.success("test");
    }

    /**
     * 测试清除jwtRealm缓存
     * MemoryConstrainedCacheManager使用的是Map作为缓存，必须用定时器清理
     */
    @GetMapping("/cache/test")
    public Result test2() {
        Cache<Object, AuthenticationInfo> authenticationCache = jwtRealm.getAuthenticationCache();
        Cache<Object, AuthorizationInfo> authorizationCache = jwtRealm.getAuthorizationCache();
        log.info("当前缓存, 认证缓存 = {} 授权缓存 = {}", authenticationCache, authorizationCache);

        authenticationCache.clear();
        authorizationCache.clear();
        log.info("缓存清除完成，认证缓存size = {} 授权缓存size = {}", authenticationCache.size(), authorizationCache.size());

        return Result.success("test");
    }
}
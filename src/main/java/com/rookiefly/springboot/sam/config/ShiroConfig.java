package com.rookiefly.springboot.sam.config;

import com.rookiefly.springboot.sam.filter.CustomPermissionsAuthorizationFilter;
import com.rookiefly.springboot.sam.filter.JwtFilter;
import com.rookiefly.springboot.sam.service.UserService;
import com.rookiefly.springboot.sam.shiro.JwtRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.Map;

import static com.rookiefly.springboot.sam.common.Constant.USER_ADD;
import static com.rookiefly.springboot.sam.common.Constant.USER_DELETE;
import static com.rookiefly.springboot.sam.common.Constant.USER_EDIT;
import static com.rookiefly.springboot.sam.common.Constant.USER_VIEW;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfig {

    // 使用@Lazy避免UserService为空
    @Lazy
    @Autowired
    UserService userService;

    @Bean
    public JwtRealm jwtRealm() {
        return new JwtRealm(userService);
    }

    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("jwtRealm") JwtRealm jwtRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 设置realm
        manager.setRealm(jwtRealm);

        /**
         * 禁止session持久化存储
         * 一定要禁止session持久化。不然清除认证缓存、授权缓存后，shiro依旧能从session中读取到认证信息
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        factoryBean.setSecurityManager(securityManager);

        //添加filter，factoryBean.getFilters()获取到的是引用，可直接添加值
        factoryBean.getFilters().put("jwt", new JwtFilter());
        factoryBean.getFilters().put("customPerms", new CustomPermissionsAuthorizationFilter());

        /**
         * factoryBean.getFilterChainDefinitionMap();默认是size=0的LinkedHashMap
         */
        Map<String, String> definitionMap = factoryBean.getFilterChainDefinitionMap();

        /**
         * definitionMap是一个LinkedHashMap，是一个链表结构
         * put的顺序很重要，当/open/**匹配到请求url后，将不再检查后面的规则
         * 官方将这种原则称为 FIRST MATCH WINS
         * https://waylau.gitbooks.io/apache-shiro-1-2-x-reference/content/III.%20Web%20Applications/10.%20Web.html
         */
        definitionMap.put("/open/**", "anon");
        definitionMap.put("/guest/**", "anon");
        definitionMap.put("/doc.html", "anon");
        definitionMap.put("/swagger-ui.html", "anon");
        definitionMap.put("/swagger-resources/**", "anon");
        definitionMap.put("/webjars/**", "anon");
        definitionMap.put("/v2/**", "anon");
        definitionMap.put("/unauthorized/**", "anon");
        /**
         * 由于禁用了session存储，shiro不会存储用户的认证状态，所以在接口授权之前要先认证用户，不然CustomPermissionsAuthorizationFilter不知道用户是谁
         * 实际项目中可以将这些接口权限规则放到数据库中去
         */
        definitionMap.put("/user/add", "jwt, customPerms[" + USER_ADD.getName() + "]");
        definitionMap.put("/user/delete", "jwt, customPerms[" + USER_DELETE.getName() + "]");
        definitionMap.put("/user/edit", "jwt, customPerms[" + USER_EDIT.getName() + "]");
        definitionMap.put("/user/view", "jwt, customPerms[" + USER_VIEW.getName() + "]");

        // 前面的规则都没匹配到，最后添加一条规则，所有的接口都要经过com.example.shirojwt.filter.JwtFilter这个过滤器验证
        definitionMap.put("/**", "jwt");

        factoryBean.setFilterChainDefinitionMap(definitionMap);

        return factoryBean;
    }
}
package com.rookiefly.springboot.sam.config;

import com.rookiefly.springboot.sam.filter.JWTFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfig {

    @Bean
    public JWTFilter jwtTokenAuthFilter() {
        return new JWTFilter();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>();
        //设置我们自定义的JWT过滤器
        filterMap.put("jwt", jwtTokenAuthFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        //必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized/无权限");
        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        chainDefinition.addPathDefinition("/doc.html", "noSessionCreation, anon");
        chainDefinition.addPathDefinition("/swagger-ui.html", "noSessionCreation, anon");
        chainDefinition.addPathDefinition("/swagger-resources/**", "noSessionCreation, anon");
        chainDefinition.addPathDefinition("/webjars/**", "noSessionCreation, anon");
        chainDefinition.addPathDefinition("/v2/**", "noSessionCreation, anon");
        chainDefinition.addPathDefinition("/unauthorized/**", "noSessionCreation, anon");
        //其余接口一律拦截，主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        chainDefinition.addPathDefinition("/**", "noSessionCreation, jwt");
        return chainDefinition;
    }

//    @Bean
//    public SecurityManager securityManager(CustomRealm customRealm) {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        // 设置自定义 realm.
//        securityManager.setRealm(customRealm);
//        /*
//         * 关闭shiro自带的session，详情见文档
//         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
//         */
//        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
//        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
//        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
//        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
//        securityManager.setSubjectDAO(subjectDAO);
//        return securityManager;
//    }
}
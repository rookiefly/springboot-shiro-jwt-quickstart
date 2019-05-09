package com.rookiefly.springboot.sam.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {RBACSourceDictConfig.BSASE_PACKAGE}, sqlSessionFactoryRef = "sqlSessionFactoryRBAC")
public class RBACSourceDictConfig {

    static final String BSASE_PACKAGE = "com.rookiefly.springboot.sam.mapper.rbac";

    static final String MAPPER_LOCATION = "classpath*:mapper/rbac/*.xml";

    static final String TYPE_ALIASES_PACKAGE = "com.rookiefly.springboot.sam.model.rbac";

    @Bean(name = "rbacDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.rbac")
    public DataSource dataSourceRBAC() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    public SqlSessionFactory sqlSessionFactoryRBAC(@Qualifier("rbacDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage(RBACSourceDictConfig.TYPE_ALIASES_PACKAGE);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(RBACSourceDictConfig.MAPPER_LOCATION));
        return factoryBean.getObject();

    }

    @Bean(name = "rbacTransactionManager")
    public DataSourceTransactionManager rbacTransactionManager(@Qualifier("rbacDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateRBAC(@Qualifier("rbacDataSource") DataSource dataSource) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryRBAC(dataSource));
        return template;
    }
}
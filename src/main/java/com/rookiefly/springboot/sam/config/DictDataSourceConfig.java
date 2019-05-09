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
@MapperScan(basePackages = {DictDataSourceConfig.BSASE_PACKAGE}, sqlSessionFactoryRef = "sqlSessionFactoryDict")
public class DictDataSourceConfig {

    static final String BSASE_PACKAGE = "com.rookiefly.springboot.sam.mapper.dict";

    static final String MAPPER_LOCATION = "classpath:mapper/dict/*.xml";

    static final String TYPE_ALIASES_PACKAGE = "com.rookiefly.springboot.sam.model.dict";

    @Bean(name = "dictDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.dict")
    public DataSource dataSourceDict() {
        return DataSourceBuilder.create().build();
    }


    @Bean
    public SqlSessionFactory sqlSessionFactoryDict(@Qualifier("dictDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeAliasesPackage(DictDataSourceConfig.TYPE_ALIASES_PACKAGE);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(DictDataSourceConfig.MAPPER_LOCATION));
        return factoryBean.getObject();

    }

    @Bean(name = "dictTransactionManager")
    public DataSourceTransactionManager dictTransactionManager(@Qualifier("dictDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateDict(@Qualifier("dictDataSource") DataSource dataSource) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryDict(dataSource));
        return template;
    }
}
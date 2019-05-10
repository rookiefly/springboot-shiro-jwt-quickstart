package com.rookiefly.springboot.sam.config.cache;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.concurrent.TimeUnit;

@EnableConfigurationProperties(GuavaProperties.class)
//@EnableCaching
//@Configuration
public class GuavaCacheConfig {

    @Autowired
    private GuavaProperties guavaProperties;

    @Bean
    public CacheBuilder<Object, Object> cacheBuilder() {
        long duration = guavaProperties.getExpireAfterWriteDuration();
        if (duration <= 0) {
            duration = 10;
        }
        return CacheBuilder.newBuilder()
                .expireAfterWrite(duration, TimeUnit.SECONDS);
    }

    /**
     * expireAfterAccess: 当缓存项在指定的时间段内没有被读或写就会被回收。
     * expireAfterWrite：当缓存项在指定的时间段内没有更新就会被回收,如果我们认为缓存数据在一段时间后数据不再可用，那么可以使用该种策略。
     * refreshAfterWrite：当缓存项上一次更新操作之后的多久会被刷新。
     *
     * @return
     */
    @DependsOn({"cacheBuilder"})
    @Bean
    public CacheManager cacheManager(CacheBuilder<Object, Object> cacheBuilder) {
        GuavaCacheManager cacheManager = new GuavaCacheManager();
        cacheManager.setCacheBuilder(cacheBuilder);
        return cacheManager;
    }
}
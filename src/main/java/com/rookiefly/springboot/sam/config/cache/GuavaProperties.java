package com.rookiefly.springboot.sam.config.cache;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "guava.cache.config")
@Getter
@Setter
public class GuavaProperties {

    private long maximumSize;

    private long maximumWeight;

    private long expireAfterWriteDuration;

    private long expireAfterAccessDuration;

    private long refreshDuration;

    private int initialCapacity;

    private int concurrencyLevel;
}
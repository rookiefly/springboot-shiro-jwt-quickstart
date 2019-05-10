package com.rookiefly.springboot.sam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SamApplication.class, args);
    }
}

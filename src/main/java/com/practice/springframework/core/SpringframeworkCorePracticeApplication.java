package com.practice.springframework.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringframeworkCorePracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringframeworkCorePracticeApplication.class, args);
    }
}

package com.ai.fdop.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MopMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MopMonitorApplication.class, args);
    }

}

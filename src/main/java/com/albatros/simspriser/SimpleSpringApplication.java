package com.albatros.simspriser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SimpleSpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(SimpleSpringApplication.class, args);
    }
}

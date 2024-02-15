package com.example.helpservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HelpServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelpServiceApplication.class, args);
    }
}

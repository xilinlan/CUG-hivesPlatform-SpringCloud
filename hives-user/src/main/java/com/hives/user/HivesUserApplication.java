package com.hives.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.hives.user.feign")
public class HivesUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(HivesUserApplication.class, args);
    }

}

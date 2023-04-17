package com.hives.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.hives.common","com.hives.user"})
@EnableFeignClients("com.hives.user.feign")
public class HivesUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(HivesUserApplication.class, args);
    }

}

package com.hives.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: zhangtao
 * @Date: 2023/4/21 20:39
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.hives.chat.feign")
public class HivesChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(HivesChatApplication.class, args);
    }

}

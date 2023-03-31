package com.hives.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HivesExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(HivesExchangeApplication.class, args);
    }

}

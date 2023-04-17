package com.hives.third;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.hives.common","com.hives.third"})
public class HivesThirdApplication {

    public static void main(String[] args) {
        SpringApplication.run(HivesThirdApplication.class, args);
    }

}

package com.hives.third.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:mail.properties")
@ConfigurationProperties(prefix = "mail")
@Data
public class MailConfig {
    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     *   accessKeySecret
     */
    private String accessKeySecret;

    /**
     * 发信地址
     */
    private String accountName;

    /**
     *区域Id
     */
    private String regionId;

    /**
     *发信人昵称
     */
    private String sendPersonName;

    /**
     *发信地址代码 1
     */
    private Integer addressType;

    /**
     *控制台创建的标签
     */
    private String tagName;

    /**
     *回信地址
     */
    private boolean replyToAddress;

    /**
     *目标地址
     */
    private String toAddress;

}

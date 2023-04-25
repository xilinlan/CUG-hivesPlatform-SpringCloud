package com.hives.chat.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xilinlan
 * @Date: 2023/04/21/19:55
 * @Description:
 */
@Data
@Document("Message")
public class MessageEntity {
    @Id
    private ObjectId id;
    /**
     * 发送者id
     */
    private Long fromId;
    /**
     * 接收者id
     */
    private Long toId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息类型
     */
    private String type;
    /**
     * 消息时间
     */
    @JsonFormat(locale = "zh", timezone = "Asia/Shanghai",pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
}

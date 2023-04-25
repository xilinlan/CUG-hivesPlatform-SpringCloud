package com.hives.chat.component;

import com.alibaba.nacos.shaded.com.google.gson.GsonBuilder;
import com.hives.chat.entity.MessageEntity;
import org.springframework.stereotype.Component;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xilinlan
 * @Date: 2023/04/21/19:57
 * @Description:
 */
@Component
public class MessageEntityDecode implements Decoder.Text<MessageEntity> {

    @Override
    public MessageEntity decode(String s) {
        // 利用 gson 处理消息实体，并格式化日期格式
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .fromJson(s, MessageEntity.class);
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {}

    @Override
    public void destroy() {}

}


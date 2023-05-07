package com.hives.chat.component;

import cn.hutool.json.JSONUtil;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.hives.chat.common.CustomSpringConfigurator;
import com.hives.chat.entity.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xilinlan
 * @Date: 2023/04/19/18:52
 * @Description:
 */

/**
 * @Author: xilinlan
 * @Description: WebSocket 服务端
 * <p>
 *     1. 通过 @ServerEndpoint 注解声明一个 WebSocket 服务端
 *     2. 通过 @OnOpen 注解声明一个连接建立时的回调
 *     3. 通过 @OnMessage 注解声明一个客户端消息到来时的回调
 *     4. 通过 @OnClose 注解声明一个连接关闭时的回调
 *     5. 通过 @OnError 注解声明一个处理错误时的回调
 *     6. 通过 @PathParam 注解可以获取客户端连接上的路径参数
 *     7. 通过 session 可以主动推送消息给客户端
 *     8. 通过 @ServerEndpoint.Configurator 注解声明一个配置器，用于注入 spring 容器中的 bean
 *     9. 通过 @ServerEndpoint.Decoders 注解声明一个解码器，用于解码客户端发送过来的消息
 *     10. 通过 @ServerEndpoint.Encoders 注解声明一个编码器，用于编码服务端发送给客户端的消息
 *     11. 通过 @ServerEndpoint.Value 注解声明一个客户端连接的 url
 *     12. 通过 @Component 注解声明一个 spring bean
 *     13. 通过 @Autowired 注解注入 spring 容器中的 bean
 *     14. 通过 @Resource 注解注入 spring 容器中的 bean
 * </p>
 */
@Component
@ServerEndpoint(
        value = "/websocket/{id}",
        decoders = { MessageEntityDecode.class },
        encoders = { MessageEntityEncode.class },
        configurator = CustomSpringConfigurator.class
)
public class WebSocketServer {

    private Session session;
    private final Gson gson;
    private RedisTemplate redis;
    /**
     * 存储每个用户的 session
     */
    private static final Map<Long, Session> WEBSOCKET_MAP = new ConcurrentHashMap<>();

    @Autowired
    public WebSocketServer(Gson gson,RedisTemplate redis) {
        this.gson = gson;
        this.redis = redis;
    }

    @OnOpen
    public void onOpen(@PathParam("id") Long id, Session session) {
        this.session = session;
        // 根据 /websocket/{id} 中传入的用户 id 作为键，存储每个用户的 session
        WEBSOCKET_MAP.put(id, session);
    }

    @OnMessage
    public void onMessage(MessageEntity message) throws IOException {
        // 根据消息实体中的消息发送者和接受者的 id 组成信箱存储的键
        // 按两人id升序并以 - 字符分隔为键
        String key = LongStream.of(message.getFromId(), message.getToId())
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("-"));

        // 将信息存储到 redis 中
        redis.opsForList().rightPush(key, JSONUtil.toJsonStr(message));
        System.out.println("message"+message);
        // 如果用户在线就将信息发送给指定用户
        if (WEBSOCKET_MAP.get(message.getToId()) != null) {
            WEBSOCKET_MAP.get(message.getToId()).getBasicRemote().sendText(gson.toJson(message));
        }
    }

    @OnClose
    public void onClose() {
        // 用户退出时，从 map 中删除信息
        for (Map.Entry<Long, Session> entry : WEBSOCKET_MAP.entrySet()) {
            if (this.session.getId().equals(entry.getValue().getId())) {
                WEBSOCKET_MAP.remove(entry.getKey());
                return;
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

}


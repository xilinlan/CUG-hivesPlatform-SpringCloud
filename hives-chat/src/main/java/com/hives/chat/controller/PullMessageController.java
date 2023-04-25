package com.hives.chat.controller;

import com.hives.chat.entity.MessageEntity;
import com.hives.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;



/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xilinlan
 * @Date: 2023/04/21/20:17
 * @Description:
 */
@RestController
@RequestMapping("/chat")
public class PullMessageController {

    private RedisTemplate redis;

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    public PullMessageController(RedisTemplate redis) {
        this.redis = redis;
    }

    @GetMapping("/pullMsg")
    public List<Object> pullMsg(@RequestParam("fromId") Long from, @RequestParam("toId") Long to) {
        // 根据两人的 id 生成键，并到 redis 中获取
        String key = LongStream.of(from, to)
                .sorted()
                .mapToObj(String::valueOf)
                .collect(Collectors.joining("-"));
        System.out.println(key);
        return redis.opsForList().range(key, 0, -1);
    }

    @GetMapping("/getMsg")
    public R GetMessage(){
//        MessageEntity messageEntity=new MessageEntity();
//        messageEntity.setFromId(3L);
//        messageEntity.setToId(4L);
//        messageEntity.setContent("hello");
//        messageEntity.setType("1");
//        messageEntity.setTime(new Date());

        // mongoTemplate.save(messageEntity);

        List<MessageEntity> all = mongoTemplate.findAll(MessageEntity.class);
        // System.out.println(all);
        return R.ok().put("data",all);
    }

}


package com.hives.chat.controller;

import com.hives.chat.entity.MessageEntity;
import com.hives.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Comparator;
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
    public R getMessage(){
        Criteria criteria1=new Criteria();
        criteria1.and("fromId").is(3).and("toId").is(4);
        Criteria criteria2=new Criteria();
        criteria2.and("fromId").is(4).and("toId").is(3);
        Criteria criteria=new Criteria();
        criteria.orOperator(criteria1,criteria2);
        Query query=new Query();
        query.addCriteria(criteria);
        List<MessageEntity> messageEntities = mongoTemplate.find(query, MessageEntity.class);
        List<MessageEntity> collect = messageEntities.stream().sorted(Comparator.comparing(MessageEntity::getTime)).collect(Collectors.toList());

        return R.ok().put("data",collect);
    }

}


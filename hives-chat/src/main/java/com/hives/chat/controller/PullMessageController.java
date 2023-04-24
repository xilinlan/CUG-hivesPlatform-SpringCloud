package com.hives.chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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

}


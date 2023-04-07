package com.hives.third.controller;

import com.hives.common.utils.R;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class EmailController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/validate/sendCode")
    public R sendCode(@RequestParam("email_code") String code){
        //TODO 发送邮件
        //接收从用户服务中传过来的code，交给第三方服务发送邮件
        //System.out.println(code);
        //System.out.println(code1);
        return R.ok();
    }
}

package com.hives.user.feign;

import com.hives.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Author: zhangtao
 * @Date: 2023/4/6 14:39
 */
@FeignClient("hives-third")
public interface EmailFeignService {

    /**
     * 发送邮件验证码
     * @param code
     * @param email
     * @return
     */
    @GetMapping("/validate/sendCode")
    R sendCode(@RequestParam("email_code") String code,@RequestParam("email") String email);

}


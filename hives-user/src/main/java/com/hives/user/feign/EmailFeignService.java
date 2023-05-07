package com.hives.user.feign;

import com.hives.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@FeignClient("hives-third-gxy")
public interface EmailFeignService {

    @GetMapping("/validate/sendCode")
    R sendCode(@RequestParam("email_code") String code,@RequestParam("email") String email);


}


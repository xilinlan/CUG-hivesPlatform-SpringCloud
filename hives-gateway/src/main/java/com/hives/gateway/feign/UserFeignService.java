package com.hives.gateway.feign;

import com.hives.common.to.FollowTo;
import com.hives.common.to.UserTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/14/16:07
 * @Description:
 */
@FeignClient("hives-user-zt")
public interface UserFeignService {
    @GetMapping("/user/user/getUserByEmail")
    UserTo UserByEmail(@RequestParam("email") String email);
}

package com.hives.chat.feign;

import com.hives.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xilinlan
 * @Date: 2023/04/21/20:39
 * @Description:
 */

@FeignClient("hives-user")
public interface UserFeignService {
    /**
     * 获取用户信息
     * @param uid
     * @return
     */
    @GetMapping("/user/follow/getFollows")
    R getFriends(@RequestParam("userId") Long uid);
}

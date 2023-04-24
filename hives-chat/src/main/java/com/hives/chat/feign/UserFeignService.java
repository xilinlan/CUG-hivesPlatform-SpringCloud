package com.hives.chat.feign;

import com.hives.common.utils.R;
import com.hives.user.entity.FollowEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xilinlan
 * @Date: 2023/04/21/20:39
 * @Description:
 */

@FeignClient("hives-user-lxl")
public interface UserFeignService {
    @GetMapping("/user/follow/getFollows")
    R getFriends(@RequestParam("userId") Long uid);
}

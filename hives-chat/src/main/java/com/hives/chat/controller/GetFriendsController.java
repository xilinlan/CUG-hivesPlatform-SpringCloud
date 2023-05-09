package com.hives.chat.controller;

import com.hives.chat.feign.UserFeignService;
import com.hives.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xilinlan
 * @Date: 2023/04/21/20:35
 * @Description:
 */
@RestController
public class GetFriendsController {

    @Autowired
    private UserFeignService userFeignService;

    @GetMapping("/getFollows")
    public R getFriends(@RequestParam("userId") Long userId) {
        return userFeignService.getFriends(userId);
    }

}


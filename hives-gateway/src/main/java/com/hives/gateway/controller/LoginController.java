package com.hives.gateway.controller;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/27/16:41
 * @Description:
 */

import com.hives.common.to.UserTo;
import com.hives.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @PostMapping("/test/login")
    public R login(@RequestBody UserTo user){
        return R.ok();
    }
}

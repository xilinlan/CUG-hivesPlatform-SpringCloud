package com.hives.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hives.common.constant.UserConstant;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hives.user.entity.UserEntity;
import com.hives.user.service.UserService;




/**
 * 用户
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:10:36
 */
@RestController
@RequestMapping("user/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody UserEntity user)
    {
        UserEntity userEntity=userService.login(user);
        return R.ok().put("user",userEntity);
    }

    @GetMapping("/sendCode")
    public R sendCode(@RequestParam String email){
        //TODO 接收到请求，检查邮箱是否合法以及数据库中已经存在邮箱，生成验证码，当验证码生成并发送到邮件后，返回ok
        System.out.println(email);
        return R.ok().put("code", UserConstant.EmailEnum.SUCCESS);
    }

    @GetMapping("/validate")
    public R validate(@RequestParam String code){
        //TODO 接收到请求，验证收到的验证码是否符合刚才生成的验证码并返回结果
        return R.ok().put("isTrue",null);
    }

    @PostMapping("/register")
    public R register(@RequestBody UserEntity user){
        //TODO 接收注册信息，将其存储到数据库表中
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("user:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:user:info")
    public R info(@PathVariable("id") Long id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:user:delete")
    public R delete(@RequestBody Long[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

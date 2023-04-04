package com.hives.user.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hives.common.constant.UserConstant;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import com.hives.user.config.MailConfig;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hives.user.entity.UserEntity;
import com.hives.user.service.UserService;

import javax.servlet.http.HttpServletRequest;


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

    /**
     * 发送验证码
     * @author xilinlan
     * @param email 邮箱
     * @return R
     */
    @PostMapping("/sendCode")
    public R sendCode(@RequestParam String email, HttpServletRequest request){
        // 接收到请求，生成验证码，当验证码生成并发送到邮件后，返回ok
        Boolean status = userService.sendCode(email, request);
        return Objects.requireNonNull(R.ok().put("status", UserConstant.EmailEnum.SUCCESS.getCode())).put("isSend", status);
    }

    /**
     * 验证验证码
     * @author xilinlan
     * @param code 验证码
     * @return R
     */
    @PostMapping("/validate")
    public R validate(@RequestParam String code, HttpServletRequest request){
        // 接收到请求，验证收到的验证码是否符合刚才生成的验证码并返回结果
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        boolean isTrue = verifyCode.equals(code);
        return R.ok().put("Correct", isTrue);
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

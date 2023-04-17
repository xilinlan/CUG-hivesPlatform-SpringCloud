package com.hives.user.controller;

import java.util.Arrays;
import java.util.Map;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hives.common.constant.UserConstant;
import com.hives.common.to.UserTo;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;

import com.hives.user.feign.EmailFeignService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import com.hives.user.entity.UserEntity;
import com.hives.user.service.UserService;
import javax.annotation.Resource;


/**
 * 用户
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:10:36
 */
@RestController
@RequestMapping("user/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailFeignService emailFeignService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录服务
     * @param user
     * @return R
     */
    @PostMapping("/login")
    public R login(@RequestBody UserEntity user)
    {
        UserEntity userEntity=userService.login(user);
        if (userEntity==null)
        {
            return R.ok().put("loginStatus", UserConstant.LoginEnum.FAIL.getCode()).put("msg",UserConstant.LoginEnum.FAIL.getMsg());
        }
        else
        {
            return R.ok().put("loginStatus", UserConstant.LoginEnum.SUCCESS.getCode()).put("msg",UserConstant.LoginEnum.SUCCESS.getMsg()).put("user",userEntity);
        }

    }

    /**
     * 注册服务
     * @param user
     * @return
     */
    @PostMapping("/register")
    public R register(@RequestBody UserEntity user) {
        // 接收注册信息，将其存储到数据库表中
        userService.register(user);
        return R.ok().put("regStatus",UserConstant.RegisterEnum.SUCCESS).put("msg",UserConstant.RegisterEnum.SUCCESS.getMsg());
    }

    /**
     * @author xilinlan
     * 生成code，然后发给第三方服务包
     * @param email
     * @return R
     */
    @GetMapping("/sendCode")
    public R sendCode(@RequestParam String email){
        // 接收到请求，检查邮箱是否合法以及数据库中已经存在邮箱，生成验证码，当验证码生成并发送到邮件后，返回ok
        System.out.println(email);
        // 生成验证码 6位
        String verifyCode = String.valueOf((Math.random()*9+1)*100000).substring(0,6);
        // 将验证码存入redis
        stringRedisTemplate.opsForValue().set(email,verifyCode,60 * 10, TimeUnit.SECONDS);
        // 调用第三方服务包将验证码发送到邮箱
        emailFeignService.sendCode(verifyCode,email);
        return R.ok().put("sendStatus", UserConstant.EmailEnum.SUCCESS.getCode()).put("msg",UserConstant.EmailEnum.SUCCESS.getMsg());
    }

    /**
     * @author xilinlan
     * 接收前端发过来的code，比对验证结果
     * @param code
     * @return R
     */
    @GetMapping("/validate")
    public R validate(@RequestParam String code,@RequestParam String email){
        // 接收到请求，从redis中取出code，比对收到的验证码是否符合刚才生成的验证码并返回结果
        String redis_code = stringRedisTemplate.opsForValue().get(email);
        if(code.equals(redis_code)){
            return R.ok().put("correct",UserConstant.ValidateEnum.SUCCESS.getCode()).put("msg",UserConstant.ValidateEnum.SUCCESS.getMsg());
        }else {
            return R.ok().put("correct",UserConstant.ValidateEnum.FAIL.getCode()).put("msg",UserConstant.ValidateEnum.FAIL.getMsg());
        }
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
    @GetMapping("/info/{id}")
    //@RequiresPermissions("user:user:info")
    public R info(@PathVariable("id") Long id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    @GetMapping("/userInfo/{id}")
    public UserTo userInfo(@PathVariable("id")Long id){
        UserTo userTo=new UserTo();
        UserEntity user=userService.getById(id);
        BeanUtils.copyProperties(user,userTo);
        System.out.println(userTo);
        return userTo;
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

    @GetMapping("/exception/test")
    public R test(){
        log.info("进入函数");
        int a=10/0;
        return R.ok();
    }

}

package com.hives.user.controller;

import java.util.Arrays;
import java.util.Map;

import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.hives.common.constant.UserConstant;
import com.hives.common.exception.RRException;
import com.hives.common.to.UserTo;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;

import com.hives.user.feign.EmailFeignService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.hives.user.entity.UserEntity;
import com.hives.user.service.UserService;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;


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
        // 检查邮箱是否存在
        Boolean checkEmail = userService.checkEmail(user.getEmail());
        if(checkEmail){
            return R.ok().put("regStatus",UserConstant.RegisterEnum.EXISTS.getCode()).put("msg",UserConstant.RegisterEnum.EXISTS.getMsg());
        }else{
            userService.register(user);
            return R.ok().put("regStatus",UserConstant.RegisterEnum.SUCCESS.getCode()).put("msg",UserConstant.RegisterEnum.SUCCESS.getMsg());
        }
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
        // 检查邮箱是否合法
        Boolean checkEmailFormat = userService.checkEmailFormat(email);
        if(Boolean.FALSE.equals(checkEmailFormat)){
            return R.ok().put("sendStatus", UserConstant.EmailEnum.ILLEGAL.getCode()).put("msg",UserConstant.EmailEnum.ILLEGAL.getMsg());
        }
        else{
            // 生成验证码 6位
            String verifyCode = String.valueOf((Math.random()*9+1)*100000).substring(0,6);
            // 将验证码存入redis
            stringRedisTemplate.opsForValue().set(email,verifyCode,60 * 10, TimeUnit.SECONDS);
            // 调用第三方服务包将验证码发送到邮箱
            emailFeignService.sendCode(verifyCode,email);
            return R.ok().put("sendStatus", UserConstant.EmailEnum.SUCCESS.getCode()).put("msg",UserConstant.EmailEnum.SUCCESS.getMsg());
        }
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
        String redisCode = stringRedisTemplate.opsForValue().get(email);
        if(code.equals(redisCode)){
            return R.ok().put("correct",UserConstant.ValidateEnum.SUCCESS.getCode()).put("msg",UserConstant.ValidateEnum.SUCCESS.getMsg());
        }else {
            return R.ok().put("correct",UserConstant.ValidateEnum.FAIL.getCode()).put("msg",UserConstant.ValidateEnum.FAIL.getMsg());
        }
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    @GetMapping("/userInfo/{id}")
    public UserTo userInfo(@PathVariable("id")Long id){
        UserTo userTo=new UserTo();
        UserEntity user=userService.getById(id);
        BeanUtils.copyProperties(user,userTo);
        return userTo;
    }

    @PostMapping("/updatePassword")
    public R updatePassword(@RequestBody UserEntity user){
        Integer status =userService.updatePassword(user);
        Integer notExist=UserConstant.UdpWEnum.NOTEXIST.getCode();
        Integer repeat=UserConstant.UdpWEnum.REPEAT.getCode();
        Integer success=UserConstant.UdpWEnum.SUCCESS.getCode();
        if (status.equals(notExist))
        {
            return R.ok().put("udpwStatus",notExist).put("msg",UserConstant.UdpWEnum.NOTEXIST.getMsg());
        }
        else if(status.equals(repeat))
        {
            return R.ok().put("udpwStatus",repeat).put("msg",UserConstant.UdpWEnum.REPEAT.getMsg());
        }
        else
        {
            return R.ok().put("udpwStatus",success).put("msg",UserConstant.UdpWEnum.SUCCESS.getMsg());
        }
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/updatePersonal")
    public R update(@RequestBody UserEntity user){
        UserEntity user1 = userService.getById(user.getId());
        if (user1==null){
            throw new RRException("该用户不存在");
        }
        userService.updateById(user);
        return R.ok().put("udppStatus",UserConstant.UdpPEnum.SUCCESS.getCode()).put("msg",UserConstant.UdpPEnum.SUCCESS.getMsg());
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/getUserByEmail")
    public UserTo userByEmail(@PathParam("email") String email){
        UserTo user=userService.getUserByEmail(email);
        return user;
    }

    @GetMapping("/infoByToken")
    public R userInfo(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            System.out.println(cookie.getValue());
            if("token".equals(cookie.getName())){
                String user = stringRedisTemplate.opsForValue().get(cookie.getValue());
                System.out.println(user);
                UserTo userTo = JSON.parseObject(user, UserTo.class);
                System.out.println(userTo);
                return R.ok().put("user",userTo);
            }
        }
        return R.error();
    }
}

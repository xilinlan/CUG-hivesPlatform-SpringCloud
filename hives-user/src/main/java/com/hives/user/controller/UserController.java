package com.hives.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    @Autowired
    private MailConfig mailConfig;//注入邮件配置类

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
        //TODO 接收到请求，生成验证码，当验证码生成并发送到邮件后，返回ok
        IClientProfile profile = DefaultProfile.getProfile(mailConfig.getRegionId(), mailConfig.getAccessKeyId(), mailConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest ssrequest = new SingleSendMailRequest();
        try {
            //ssrequest.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
            ssrequest.setAccountName(mailConfig.getAccountName());
            ssrequest.setFromAlias("CUG-hives内容分享平台");
            ssrequest.setAddressType(mailConfig.getAddressType());
            ssrequest.setTagName(mailConfig.getTagName());
            ssrequest.setReplyToAddress(true);
            ssrequest.setToAddress(email);
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //ssrequest.setToAddress("邮箱1,邮箱2");
            ssrequest.setSubject("尊敬的用户您好:");
            //随机生成六位验证码
            String verifyCode = String.valueOf((Math.random()*9+1)*100000).substring(0,6);
            request.getSession().setAttribute("verifyCode",verifyCode);
            // 设置session过期时间为5分钟
            request.getSession().setMaxInactiveInterval(5*60);
            ssrequest.setHtmlBody("您的账号注册验证码为：<h3>"+verifyCode+"</h3>");
//            开启需要备案，0关闭，1开启
            ssrequest.setClickTrace("0");
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = client.getAcsResponse(ssrequest);
            System.out.println(httpResponse.getRequestId());
        } catch (ClientException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
        }
        System.out.println(email);
        return R.ok();
    }

    /**
     * 验证验证码
     * @author xilinlan
     * @param code 验证码
     * @return R
     */
    @PostMapping("/validate")
    public R validate(@RequestParam String code, HttpServletRequest request){
        //TODO 接收到请求，验证收到的验证码是否符合刚才生成的验证码并返回结果
        String verifyCode = (String) request.getSession().getAttribute("verifyCode");
        boolean isTrue = verifyCode.equals(code);
        return R.ok().put("isTrue", isTrue);
    }

    @PostMapping("/register")
    public R register(@RequestBody UserEntity user){
        //TODO 接收注册信息，将其存储到数据库表中
        return R.ok();
    }

    @PostMapping("/checkEmail")
    public R checkEmail(@RequestBody UserEntity user){
        //TODO 接收前端传来的email（存储在user.email属性中），查询数据库表该邮箱是否存在

        return R.ok().put("checkCode",null);
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

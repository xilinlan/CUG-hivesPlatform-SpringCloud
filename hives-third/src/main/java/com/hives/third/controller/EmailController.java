package com.hives.third.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.hives.common.utils.R;
import com.hives.third.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class EmailController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MailConfig mailConfig;

    @GetMapping("/validate/sendCode")
    public R sendCode(@RequestParam("email_code") String code,@RequestParam("email") String email){
        //发送邮件
        //接收从用户服务中传过来的code，交给第三方服务发送邮件
        //邮件发送结果
        // 配置邮箱发送的基本参数
        IClientProfile profile = DefaultProfile.getProfile(mailConfig.getRegionId(), mailConfig.getAccessKeyId(), mailConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName(mailConfig.getAccountName());
            request.setFromAlias("CUG-Hives平台");
            request.setAddressType(mailConfig.getAddressType());
            request.setTagName(mailConfig.getTagName());
            request.setReplyToAddress(true);
            request.setToAddress(email);
            System.out.println(email);
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress("邮箱1,邮箱2");
            request.setSubject("尊敬的用户您好:");
            //随机生成六位验证码
            request.setHtmlBody("【HivesPlatform】您的验证码为：<h3>"+code+"</h3><p>切勿将验证码泄露于他人,本条验证码有效期10分钟</p>");
            //开启需要备案，0关闭，1开启
            request.setClickTrace("0");
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            String result = httpResponse.getEnvId() + " "+httpResponse.getRequestId();
            System.out.println(result);
        } catch (ClientException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
        }

        return R.ok();
    }
}

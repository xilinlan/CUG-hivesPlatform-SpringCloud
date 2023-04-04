package com.hives.user.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.user.config.MailConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.user.dao.UserDao;
import com.hives.user.entity.UserEntity;
import com.hives.user.service.UserService;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private MailConfig mailConfig;//注入邮件配置类
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserEntity login(UserEntity user) {
        String password = user.getPassword();
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        UserEntity userEntity = this.getOne(new QueryWrapper<UserEntity>().eq("email", user.getEmail()).eq("password", password));
        return userEntity;
    }

    @Override
    public Boolean sendCode(String email,HttpServletRequest request) {
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
        return true;
    }


}

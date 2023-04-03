package com.hives.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;

import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import com.hives.user.config.MailConfig;
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
    @Autowired
    private MailConfig mailConfig;

    /**
     * 邮件发送
     * @return
     */
    @GetMapping("/mail")
    public String sample() {
        //邮件发送结果
        String result = "";

        // 如果是除杭州region外的其它region（如新加坡、澳洲Region），需要将下面的"cn-hangzhou"替换为"ap-southeast-1"、或"ap-southeast-2"。
        IClientProfile profile = DefaultProfile.getProfile(mailConfig.getRegionId(), mailConfig.getAccessKeyId(), mailConfig.getAccessKeySecret());
        // 如果是除杭州region外的其它region（如新加坡region）， 需要做如下处理
        //try {
        //DefaultProfile.addEndpoint("dm.ap-southeast-1.aliyuncs.com", "ap-southeast-1", "Dm",  "dm.ap-southeast-1.aliyuncs.com");
        //} catch (ClientException e) {
        //e.printStackTrace();
        //}
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            //request.setVersion("2017-06-22");// 如果是除杭州region外的其它region（如新加坡region）,必须指定为2017-06-22
            request.setAccountName(mailConfig.getAccountName());
            request.setFromAlias("CUG-hives内容分享平台");
            request.setAddressType(mailConfig.getAddressType());
            request.setTagName(mailConfig.getTagName());
            request.setReplyToAddress(true);
            request.setToAddress(mailConfig.getToAddress());
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress("邮箱1,邮箱2");
            request.setSubject("尊敬的用户您好:");
            //随机生成六位验证码
            String verifyCode = String.valueOf((Math.random()*9+1)*100000).substring(0,6);
            request.setHtmlBody("此邮件为开发邮件功能的测试邮件，验证码为："+verifyCode);
//            开启需要备案，0关闭，1开启
            request.setClickTrace("0");
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            result = httpResponse.getEnvId() + " "+httpResponse.getRequestId();
        } catch (ClientException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
        }
        return result;
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

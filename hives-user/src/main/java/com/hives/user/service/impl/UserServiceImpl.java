package com.hives.user.service.impl;

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

}

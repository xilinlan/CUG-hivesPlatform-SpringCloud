package com.hives.user.service.impl;

import com.hives.common.to.UserTo;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.user.config.MailConfig;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
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

/**
 * @Author: zhangtao and xilinlan
 * @date 2023-03-30 14:10:36
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    /**
     * 注入邮件配置类
     */
    @Autowired
    private MailConfig mailConfig;
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

        password= BCrypt.hashpw(password,BCrypt.gensalt());
        UserEntity userEntity = this.getOne(new QueryWrapper<UserEntity>().eq("email", user.getEmail()).eq("password", password));
        return userEntity;
    }

    @Override
    public UserEntity register(UserEntity user) {
        String password = user.getPassword();
        password= BCrypt.hashpw(password,BCrypt.gensalt());
        user.setPassword(password);
        this.save(user);
        return user;
    }

    @Override
    public Boolean checkEmail(String email) {
        UserEntity userEntity = this.getOne(new QueryWrapper<UserEntity>().eq("email", email));
        return userEntity != null;
    }

    @Override
    public UserTo getUserByEmail(String email) {
        UserEntity user = this.getOne(new QueryWrapper<UserEntity>().eq("email", email));
        UserTo userTo=new UserTo();
        BeanUtils.copyProperties(user,userTo);
        return userTo;
    }

    @Override
    public Boolean checkEmailFormat(String email) {
        // 邮箱格式校验
        String regex = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return email.matches(regex);
    }

    @Override
    public Integer updatePassword(UserEntity user) {
        String password = user.getPassword();

        password= BCrypt.hashpw(password,BCrypt.gensalt());
        //先查询用户是否存在
        UserEntity userEntity = this.getOne(new QueryWrapper<UserEntity>().eq("email", user.getEmail()));

        if (userEntity == null) {
            // 用户不存在
            return 2;
        }
        else if(BCrypt.checkpw(user.getPassword(),userEntity.getPassword()))
        {
            // 新密码不能与旧密码相同
            return 3;
        }
        else
        {
            // 更新密码
            userEntity.setPassword(password);
            this.updateById(userEntity);
            return 1;
        }

    }

}

package com.hives.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.common.to.UserTo;
import com.hives.common.utils.PageUtils;
import com.hives.user.entity.UserEntity;

import java.util.Map;

/**
 * 用户
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:10:36
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 分页查询
     * @param params
     * @return PageUtils
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 登录
     * @param user
     * @return UserEntity
     */
    UserEntity login(UserEntity user);

    /**
     * 注册
     * @param user
     * @return UserEntity
     */
    UserEntity register(UserEntity user);

    /**
     * 修改密码
     * @param user
     * @return Integer
     */
    Integer updatePassword(UserEntity user);

    /**
     * 检测邮箱是否存在
     * @param email
     * @return Boolean
     */
    Boolean checkEmail(String email);

    /**
     * 根据邮箱获取用户信息
     * @param email
     * @return UserTo
     */
    UserTo getUserByEmail(String email);

    /**
     * 检测邮箱格式是否正确
     * @param email
     * @return Boolean
     */
    Boolean checkEmailFormat(String email);
}


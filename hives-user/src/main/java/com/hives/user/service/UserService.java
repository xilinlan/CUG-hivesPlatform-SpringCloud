package com.hives.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
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

    PageUtils queryPage(Map<String, Object> params);

    UserEntity login(UserEntity user);

    UserEntity register(UserEntity user);

    Integer updatePassword(UserEntity user);




}


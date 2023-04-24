package com.hives.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.common.utils.PageUtils;
import com.hives.user.entity.FollowEntity;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:10:36
 */
public interface FollowService extends IService<FollowEntity> {

    /**
     * 分页查询
     * @param params
     * @return 分页对象
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 关注
     * @param userId
     * @return 关注对象
     */
    List<FollowEntity> getFollow(Long userId);
}


package com.hives.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.common.to.FollowTo;
import com.hives.common.utils.PageUtils;
import com.hives.user.entity.FollowEntity;
import com.hives.user.vo.OtherUserVo;
import com.hives.user.vo.FollowerVo;

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
    List<FollowerVo> getFollow(Long userId);

    /**
     * 关注
     * @param userId
     * @param targetId
     * @return 关注对象信息以及关注状态
     */
    OtherUserVo getOtherUserInfo(Long userId, Long targetId);

    /**
     * 保存关注信息
     * @param follow
     * @return 关注对象信息以及关注状态
     */
    void saveFollow(FollowEntity follow);

    /**
     * 删除关注信息
     * @param follow
     * @return 关注对象信息以及关注状态
     */
    void deleteFollow(FollowEntity follow);

    /**
     * 获取关注列表
     * @param userId
     * @return 关注对象信息以及关注状态
     */
    List<FollowTo> getFollowTo(Long userId);
}


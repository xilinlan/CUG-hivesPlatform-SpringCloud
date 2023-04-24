package com.hives.user.service.impl;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.user.entity.UserEntity;
import com.hives.user.service.UserService;
import com.hives.user.vo.FollowerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.user.dao.FollowDao;
import com.hives.user.entity.FollowEntity;
import com.hives.user.service.FollowService;


/**
 * @author xilinaln
 */
@Service("followService")
public class FollowServiceImpl extends ServiceImpl<FollowDao, FollowEntity> implements FollowService {

    @Autowired
    private UserService userService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FollowEntity> page = this.page(
                new Query<FollowEntity>().getPage(params),
                new QueryWrapper<FollowEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<FollowerVo> getFollow(Long userId) {
        List<FollowEntity> entityList = this.list(new QueryWrapper<FollowEntity>().eq("user_id", userId));

        List<Long> targetIdList = entityList.stream().map(item -> {
            return item.getTargetId();
        }).collect(Collectors.toList());

        if(targetIdList.size() == 0) {
            return null;
        }
        List<UserEntity> userEntityList = userService.listByIds(targetIdList);

        List<FollowerVo> collect = userEntityList.stream().map(item -> {
            FollowerVo followerVo = new FollowerVo();
            followerVo.setNickname(item.getNickname());
            followerVo.setHeader(item.getHeader());
            followerVo.setTargetId(item.getId());
            followerVo.setUserId(userId);
            followerVo.setId(entityList.stream().filter(entity -> entity.getTargetId().equals(item.getId())).findFirst().get().getId());
            return followerVo;
        }).collect(Collectors.toList());

        return collect;
    }

}

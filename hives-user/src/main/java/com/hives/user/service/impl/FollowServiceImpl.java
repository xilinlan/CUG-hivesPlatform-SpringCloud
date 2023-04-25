package com.hives.user.service.impl;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.user.entity.UserEntity;
import com.hives.user.service.UserService;
import com.hives.user.vo.FollowerVo;
import com.hives.user.vo.OtherUserVo;
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

    @Override
    public OtherUserVo getOtherUserInfo(Long userId, Long targetId) {
        Boolean isFollow = false;
        //1.查询targetId的用户信息
        UserEntity userEntity = userService.getById(targetId);
        if(userEntity == null) {
            return null;
        }
        //2.查询是否关注
        FollowEntity followEntity = this.getOne(new QueryWrapper<FollowEntity>().eq("user_id", userId).eq("target_id", targetId).eq("is_deleted", 0));
        isFollow = followEntity != null;
        OtherUserVo otherUserVo = new OtherUserVo();
        otherUserVo.setId(userEntity.getId());
        otherUserVo.setNickname(userEntity.getNickname());
        otherUserVo.setHeader(userEntity.getHeader());
        otherUserVo.setEmail(userEntity.getEmail());
        otherUserVo.setBackground(userEntity.getBackground());
        otherUserVo.setGender(userEntity.getGender());
        otherUserVo.setBirthday(userEntity.getBirthday());
        otherUserVo.setPhoneNumber(userEntity.getPhoneNumber());
        otherUserVo.setFollowCount(userEntity.getFollowCount());
        otherUserVo.setFansCount(userEntity.getFansCount());
        otherUserVo.setCreateTime(userEntity.getCreateTime());
        otherUserVo.setLastTime(userEntity.getLastTime());
        //3.是否关注信息
        otherUserVo.setIsFollow(isFollow);
        return otherUserVo;
    }

}

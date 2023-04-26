package com.hives.user.service.impl;

import com.hives.common.exception.RRException;
import com.hives.common.to.FollowTo;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.user.entity.UserEntity;
import com.hives.user.service.UserService;
import com.hives.user.vo.FollowerVo;
import com.hives.user.vo.OtherUserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.user.dao.FollowDao;
import com.hives.user.vo.FollowerVo;
import com.hives.user.entity.FollowEntity;
import com.hives.user.service.FollowService;
import org.springframework.transaction.annotation.Transactional;


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
        List<FollowEntity> entityList = this.list(new QueryWrapper<FollowEntity>().eq("user_id", userId).eq("is_deleted",0));

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFollow(FollowEntity follow) {
        FollowEntity followEntity = this.getOne(new QueryWrapper<FollowEntity>().eq("user_id", follow.getUserId()).eq("target_id", follow.getTargetId()));

        if(followEntity==null){
            this.save(follow);
        }else if(followEntity.getIsDeleted()==1){
            followEntity.setIsDeleted(0);
            this.updateById(followEntity);
        }else{
            throw new RRException("系统繁忙，请稍后再试",11000);
        }
        UserEntity user = userService.getById(follow.getUserId());
        UserEntity targetUser=userService.getById(follow.getTargetId());
        user.setFollowCount(user.getFollowCount()+1);
        targetUser.setFansCount(targetUser.getFansCount()+1);
        userService.updateById(user);
        userService.updateById(targetUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFollow(FollowEntity follow) {
        FollowEntity followEntity = this.getOne(new QueryWrapper<FollowEntity>().eq("user_id", follow.getUserId()).eq("target_id", follow.getTargetId()));

        if (followEntity != null) {
            followEntity.setIsDeleted(1);
            this.updateById(followEntity);
        }

        UserEntity user = userService.getById(follow.getUserId());
        UserEntity targetUser=userService.getById(follow.getTargetId());
        user.setFollowCount(user.getFollowCount()-1);
        targetUser.setFansCount(targetUser.getFansCount()-1);
        userService.updateById(user);
        userService.updateById(targetUser);

    }

    @Override
    public List<FollowTo> getFollowTo(Long userId) {
        List<FollowEntity> list = this.list(new QueryWrapper<FollowEntity>().eq("user_id", userId).eq("is_deleted", 0));
        List<FollowTo> collect = list.stream().map(item -> {
            FollowTo followTo = new FollowTo();
            BeanUtils.copyProperties(item, followTo);
            return followTo;
        }).collect(Collectors.toList());
        return collect;
    }
}

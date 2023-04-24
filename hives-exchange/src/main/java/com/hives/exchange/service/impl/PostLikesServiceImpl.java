package com.hives.exchange.service.impl;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.exchange.config.CacheRemove;
import com.hives.exchange.entity.PostEntity;
import com.hives.exchange.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.exchange.dao.PostLikesDao;
import com.hives.exchange.entity.PostLikesEntity;
import com.hives.exchange.service.PostLikesService;
import org.springframework.transaction.annotation.Transactional;


@Service("postLikesService")
public class PostLikesServiceImpl extends ServiceImpl<PostLikesDao, PostLikesEntity> implements PostLikesService {
    @Autowired
    @Lazy
    private PostService postService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PostLikesEntity> page = this.page(
                new Query<PostLikesEntity>().getPage(params),
                new QueryWrapper<PostLikesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PostLikesEntity isLike(Long userId, Long id) {
        PostLikesEntity postLike = this.getOne(new QueryWrapper<PostLikesEntity>().eq("user_id", userId).eq("post_id", id).eq("is_deleted", 0));
        return postLike;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheRemove(value = "postCache",key={"getUserCollects_","queryPostPage_"})
    public void updatePostLikes(Long userId,Long postId) {
        PostEntity post = postService.getById(postId);
        Long likes = post.getLikes();
        PostLikesEntity likesEntity = this.getOne(new QueryWrapper<PostLikesEntity>().eq("user_id", userId).eq("post_id", postId));
        if(likesEntity==null){
            PostLikesEntity postLikesEntity = new PostLikesEntity();
            postLikesEntity.setUserId(userId);
            postLikesEntity.setPostId(postId);
            this.save(postLikesEntity);
            return;
        }
        System.out.println(post.getId());
        if(likesEntity.getIsDeleted()==0){
            likesEntity.setIsDeleted(1);
            post.setLikes(likes-1);
            postService.updateById(post);
        }else{
            likesEntity.setIsDeleted(0);
            post.setLikes(likes+1);
            postService.updateById(post);
        }
        this.updateById(likesEntity);
    }

    @Override
    public void removePostLikesByPostId(Long postId) {
        List<PostLikesEntity> likesEntities = this.list(new QueryWrapper<PostLikesEntity>().eq("post_id", postId));
        List<PostLikesEntity> collect = likesEntities.stream().map(item -> {
            item.setIsDeleted(1);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }

}
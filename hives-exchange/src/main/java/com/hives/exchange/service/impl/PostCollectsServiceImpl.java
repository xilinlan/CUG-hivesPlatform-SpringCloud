package com.hives.exchange.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.exchange.config.CacheRemove;
import com.hives.exchange.entity.PostEntity;
import com.hives.exchange.entity.PostLikesEntity;
import com.hives.exchange.service.PostService;
import com.hives.exchange.vo.PostVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.exchange.dao.PostCollectsDao;
import com.hives.exchange.entity.PostCollectsEntity;
import com.hives.exchange.service.PostCollectsService;


@Service("postCollectsService")
public class PostCollectsServiceImpl extends ServiceImpl<PostCollectsDao, PostCollectsEntity> implements PostCollectsService {
    @Autowired
    @Lazy
    private PostService postService;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PostCollectsEntity> page = this.page(
                new Query<PostCollectsEntity>().getPage(params),
                new QueryWrapper<PostCollectsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PostCollectsEntity isCollect(Long userId, Long id) {
        PostCollectsEntity postCollect = this.getOne(new QueryWrapper<PostCollectsEntity>().eq("user_id", userId).eq("post_id", id).eq("is_deleted", 0));

        return postCollect;
    }

    @Override
    @CacheRemove(value = "postCollects", key={"getUserCollects_","queryPostPage_"})
    public void updateCollects(Long userId, Long postId) {
        PostEntity post = postService.getById(postId);
        Long collects = post.getCollects();
        PostCollectsEntity collectsEntity = this.getOne(new QueryWrapper<PostCollectsEntity>().eq("user_id", userId).eq("post_id", postId));
        if(collectsEntity==null){
            PostCollectsEntity postCollectsEntity = new PostCollectsEntity();
            postCollectsEntity.setUserId(userId);
            postCollectsEntity.setPostId(postId);
            post.setCollects(collects+1);
            postService.updateById(post);
            this.save(postCollectsEntity);
            return;
        }
        if(collectsEntity.getIsDeleted()==0){
            collectsEntity.setIsDeleted(1);
            post.setCollects(collects-1);
            postService.updateById(post);
        }else{
            collectsEntity.setIsDeleted(0);
            post.setCollects(collects+1);
            postService.updateById(post);
        }
        this.updateById(collectsEntity);
    }

    @Override
    @Cacheable(value="postCollects",key = "#root.method.name + '_' + #userId + '_' + #params.get('page')")
    public PageUtils getUserCollects(Map<String, Object> params,Long userId) {
        IPage<PostCollectsEntity> page = this.page(
                new Query<PostCollectsEntity>().getPage(params),
                new QueryWrapper<PostCollectsEntity>().eq("user_id",userId)
        );
        List<PostCollectsEntity> collectsEntityList = page.getRecords();
        List<Long> postIdCollect = collectsEntityList.stream().map(item -> item.getPostId()).collect(Collectors.toList());

        List<PostEntity> postEntityList = postService.listByIds(postIdCollect).stream().sorted(Comparator.comparing(PostEntity::getUpdateTime).reversed()).collect(Collectors.toList());

        List<PostVo> postVoList = postService.getPostVoList(userId, postEntityList);

        PageUtils pageUtils=new PageUtils(page);
        pageUtils.setList(postVoList);
        return pageUtils;
    }

    @Override
    public void removePostCollectsByPostId(Long postId) {
        List<PostCollectsEntity> collectsEntities = this.list(new QueryWrapper<PostCollectsEntity>().eq("post_id", postId));
        List<PostCollectsEntity> collect = collectsEntities.stream().map(item -> {
            item.setIsDeleted(1);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }
}
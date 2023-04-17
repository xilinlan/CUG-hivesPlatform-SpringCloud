package com.hives.exchange.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hives.common.to.UserTo;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.common.utils.R;
import com.hives.exchange.config.CacheRemove;
import com.hives.exchange.dto.PostDto;
import com.hives.exchange.entity.PostCollectsEntity;
import com.hives.exchange.entity.PostImagesEntity;
import com.hives.exchange.entity.PostLikesEntity;
import com.hives.exchange.feign.UserFeignService;
import com.hives.exchange.service.PostCollectsService;
import com.hives.exchange.service.PostImagesService;
import com.hives.exchange.service.PostLikesService;
import com.hives.exchange.vo.PostVo;
import netscape.javascript.JSObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.exchange.dao.PostDao;
import com.hives.exchange.entity.PostEntity;
import com.hives.exchange.service.PostService;
import org.springframework.transaction.annotation.Transactional;


@Service("postService")
public class PostServiceImpl extends ServiceImpl<PostDao, PostEntity> implements PostService {
    @Autowired
    private PostImagesService postImagesService;

    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private PostLikesService postLikesService;

    @Autowired
    private PostCollectsService postCollectsService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PostEntity> page = this.page(
                new Query<PostEntity>().getPage(params),
                new QueryWrapper<PostEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @CacheEvict
    public void savePost(PostDto post) {
        PostEntity postEntity=new PostEntity();
        BeanUtils.copyProperties(post,postEntity);
        //SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        postEntity.setCreateTime(new Date());
        postEntity.setUpdateTime(new Date());
        this.save(postEntity);

        List<String> urls = post.getUrls();
        List<PostImagesEntity> collect = urls.stream().map(item -> {
            PostImagesEntity postImagesEntity = new PostImagesEntity();
            postImagesEntity.setUrl(item);
            postImagesEntity.setPostId(postEntity.getId());
            return postImagesEntity;
        }).collect(Collectors.toList());

        postImagesService.saveBatch(collect);
    }



    @Override
    @Transactional
    @Cacheable(value = "postCache" , key = "#root.method.name + '_' + #userId + '_' + #params.get('page')")
    public PageUtils queryPostPage(Map<String, Object> params , Long userId){
        IPage<PostEntity> page = this.page(
                new Query<PostEntity>().getPage(params),
                new QueryWrapper<PostEntity>()
        );
        PageUtils pageUtils=new PageUtils(page);
        List<PostEntity>postList=page.getRecords();

        List<PostVo> collect = postList.stream().map(item -> {
            PostVo postVo = new PostVo();
            BeanUtils.copyProperties(item, postVo);

            CompletableFuture<Void>imagesFuture=CompletableFuture.runAsync(()->{
                List<String>images=postImagesService.getImages(item.getId());
                postVo.setUrls(images);
            },threadPoolExecutor);

            CompletableFuture<Void>userFuture=CompletableFuture.runAsync(()->{
                UserTo userTo = userFeignService.userInfo(item.getUserId());
                postVo.setEmail(userTo.getEmail());
                postVo.setHeader(userTo.getHeader());
                postVo.setNickname(userTo.getNickname());
                postVo.setHot(0L);
            },threadPoolExecutor);

            CompletableFuture<Void>postLoveFuture=CompletableFuture.runAsync(()->{
                PostLikesEntity postLikesEntity=postLikesService.isLike(userId,item.getId());
                postVo.setIsLove(postLikesEntity!=null);
            },threadPoolExecutor);

            CompletableFuture<Void>postCollectFuture=CompletableFuture.runAsync(()->{
                PostCollectsEntity postCollectsEntity=postCollectsService.isCollect(userId,item.getId());
                postVo.setIsCollect(postCollectsEntity!=null);
            },threadPoolExecutor);


            try {
                CompletableFuture.allOf(imagesFuture,userFuture,postLoveFuture,postCollectFuture).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }

            return postVo;
        }).collect(Collectors.toList());
        pageUtils.setList(collect);

        return pageUtils;
    }

    @Override
    @CacheRemove(value = "postCache",key = "queryPostPage_")
    public void cacheTest(Long userId) {
        System.out.println("调用方法");
    }
}
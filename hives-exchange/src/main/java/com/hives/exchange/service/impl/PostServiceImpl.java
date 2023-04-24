package com.hives.exchange.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hives.common.constant.PostConstant;
import com.hives.common.to.UserTo;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.common.utils.R;
import com.hives.exchange.config.CacheRemove;
import com.hives.exchange.dto.PostDto;
import com.hives.exchange.entity.*;
import com.hives.exchange.feign.UserFeignService;
import com.hives.exchange.service.*;
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
    private PostVideoService postVideoService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PostEntity> page = this.page(
                new Query<PostEntity>().getPage(params),
                new QueryWrapper<PostEntity>()
        );
        System.out.println(page.getSize());
        System.out.println(page.getRecords().size());
        return new PageUtils(page);
    }

    @Override
    @CacheRemove(value = "postCache", key="queryPostPage_")
    public void savePost(Long userId,PostDto post) {
        PostEntity postEntity=new PostEntity();
        BeanUtils.copyProperties(post,postEntity);
        //SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        postEntity.setCreateTime(new Date());
        postEntity.setUpdateTime(new Date());
        this.save(postEntity);

        Integer type = post.getType();
        List<String> urls = post.getUrls();
        if(type== PostConstant.PostTypeEnum.COMMON.getCode()){
            List<PostImagesEntity> collect = urls.stream().map(item -> {
                PostImagesEntity postImagesEntity = new PostImagesEntity();
                postImagesEntity.setUrl(item);
                postImagesEntity.setPostId(postEntity.getId());
                return postImagesEntity;
            }).collect(Collectors.toList());
            postImagesService.saveBatch(collect);
        }else{
            List<PostVideoEntity> collect = urls.stream().map(item -> {
                PostVideoEntity postVideoEntity = new PostVideoEntity();
                postVideoEntity.setUrl(item);
                postVideoEntity.setPostId(postEntity.getId());
                return postVideoEntity;
            }).collect(Collectors.toList());
            postVideoService.saveBatch(collect);
        }
    }


    /**
     * 获取贴子方法，使用SpringCache管理缓存,如果查询缓存没有结果，则会执行函数中的业务流程
     * @param params
     * @param userId
     * @return
     */
    @Override
    @Transactional
    @Cacheable(value = "postCache" , key = "#root.method.name + '_' + #userId + '_' + #params.get('page')")
    public PageUtils queryPostPage(Map<String, Object> params , Long userId){
        // 按params对象中存储的page和limit对象去对应页内容，具体的查询条件写在第二个QueryWrapper里
        IPage<PostEntity> page = this.page(
                new Query<PostEntity>().getPage(params),
                new QueryWrapper<PostEntity>().eq("is_deleted",0).orderByDesc("update_time")
        );

        PageUtils pageUtils=new PageUtils(page);
        List<PostEntity>postList=page.getRecords();

        // 调用getPostVoList方法，获取返回前端所需的数据
        List<PostVo>collect=getPostVoList(userId,postList);

        pageUtils.setList(collect);

        return pageUtils;
    }

    /**
     * PostEntity对象:{id,content,createTime,updateTime,userId,collects,likes,reply,type}
     * PostVo对象:{...,email,header,urls,nickname,isCollect,isLove,urls}即除基本的贴子id以外的其他展示信息
     * 该函数的目的是在PostEntity的基础上对其他表进行查询得到展示类PostVo返回
     * @param userId
     * @param postList
     * @return
     */
    @Override
    @Transactional
    public List<PostVo> getPostVoList(Long userId , List<PostEntity> postList) {
        List<PostVo> collect = postList.stream().map(item -> {
            PostVo postVo = new PostVo();
            BeanUtils.copyProperties(item, postVo);

            // Completable异步查询数据库表并完成数据的组装，包括图片表，用户信息表，是否关注和是否收藏
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
        return collect;
    }

    @Override
    public PageUtils queryOwnPage(Map<String, Object> params, Long userId) {
        // 查询出对应userId的page
        IPage<PostEntity> page = this.page(
                new Query<PostEntity>().getPage(params),
                new QueryWrapper<PostEntity>().eq("user_id",userId).eq("is_deleted",0).orderByDesc("create_time")

        );
        PageUtils pageUtils=new PageUtils(page);
        List<PostEntity>postList=page.getRecords();

        List<PostVo>collect=getPostVoList(userId,postList);

        pageUtils.setList(collect);

        return pageUtils;
    }

    /**
     * 对传入的asList中带有的id对应的贴子做逻辑删除，并清除缓存
     * @param asList
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict={
            @CacheEvict(value = "postCache",allEntries = true)
    })
    public void logicRemoveByIds(List<Long> asList) {
        List<PostEntity> postEntityList = this.listByIds(asList);
        List<PostEntity> collect = postEntityList.stream().map(item -> {
            CompletableFuture<Void>postFuture=CompletableFuture.runAsync(()->{
                item.setIsDeleted(1);
            },threadPoolExecutor);

            CompletableFuture<Void>collectsFuture=CompletableFuture.runAsync(()->{
                postCollectsService.removePostCollectsByPostId(item.getId());
            },threadPoolExecutor);

            CompletableFuture<Void>likesFuture=CompletableFuture.runAsync(()->{
                postLikesService.removePostLikesByPostId(item.getId());
            },threadPoolExecutor);

            CompletableFuture<Void>imagesFuture=CompletableFuture.runAsync(()->{
                postImagesService.removeImagesByPostId(item.getId());
            },threadPoolExecutor);

            CompletableFuture<Void>videoFuture=CompletableFuture.runAsync(()->{
                postVideoService.removeVideoByPostId(item.getId());
            },threadPoolExecutor);

            CompletableFuture<Void> cfAll = CompletableFuture.allOf(postFuture, collectsFuture, likesFuture,imagesFuture,videoFuture);
            try {
                cfAll.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
            return item;
        }).collect(Collectors.toList());

        this.updateBatchById(collect);
        replyService.removeReplyByPostIds(asList);
    }

    @Override
    public void updatePostUpdateTime(Long postId) {
        PostEntity post = this.getById(postId);
        post.setReply(post.getReply()+1);
        post.setUpdateTime(new Date());
        this.updateById(post);
    }

    @Override
    public void removeReply(Long postId) {
        PostEntity post = this.getById(postId);
        post.setReply(post.getReply()-1);
        post.setUpdateTime(new Date());
        this.updateById(post);
    }
}


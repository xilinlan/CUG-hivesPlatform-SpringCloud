package com.hives.exchange.service.impl;

import com.hives.common.to.UserTo;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.exchange.feign.UserFeignService;
import com.hives.exchange.service.PostService;
import com.hives.exchange.service.ReplyLikesService;
import com.hives.exchange.vo.Reply1Vo;
import com.hives.exchange.vo.Reply2Vo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.exchange.dao.ReplyDao;
import com.hives.exchange.entity.ReplyEntity;
import com.hives.exchange.service.ReplyService;


@Service("replyService")
public class ReplyServiceImpl extends ServiceImpl<ReplyDao, ReplyEntity> implements ReplyService {
    @Autowired
    private UserFeignService userFeignService;

    @Autowired
    private ReplyLikesService replyLikesService;

    @Autowired
    @Lazy
    private PostService postService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReplyEntity> page = this.page(
                new Query<ReplyEntity>().getPage(params),
                new QueryWrapper<ReplyEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @CacheEvict(value = "replyCache",key="'getFirstLevelComments'+#reply.getPostId()")
    public ReplyEntity saveReply(ReplyEntity reply) {
        reply.setCreateTime(new Date());
        postService.updatePostUpdateTime(reply.getPostId());
        this.save(reply);
        return reply;
    }

    /**
     * 取出一个贴子下的所有回复，其中回复分为一级回复和二级回复，因此定义Reply1Vo和Reply2Vo，两者关系为Reply1Vo:{...,List<Reply2Vo>}
     * 缓存为每个贴子第一次取会有一个缓存，在贴子下的内容有更新时会刷新缓存
     * @param postId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Override
    @Cacheable(value = "replyCache",key = "#root.method.name+ #postId")
    public List<Reply1Vo> getFirstLevelComments(Long postId) throws ExecutionException, InterruptedException {
        List<Reply1Vo> reply1VoList=new ArrayList<>();
        // 取出所有的回复，供下面的代码对回复进行封装
        List<ReplyEntity> replyEntityList = this.list(new QueryWrapper<ReplyEntity>().eq("post_id", postId));
        Map<Long,Integer>Reply1LocMap=new HashMap<>();

        // 逐一遍历ReplyEntity对象
        for (ReplyEntity item:replyEntityList){
            if(item.getReply1Id()==0){
                // 如果是一级回复，那么直接放在要返回的List中，并在map中保留该一级回复id的位置
                Reply1Vo reply1Vo = buildReply1(item);
                reply1VoList.add(reply1Vo);
                Reply1LocMap.put(item.getId(),reply1VoList.size()-1);
            }else{
                // 如果是二级回复，通过map找到对应的一级回复位置添加在一级回复ReplyVo的List对象数组中
                Reply2Vo reply2Vo=buildReply2(item);
                Integer loc=Reply1LocMap.get(item.getReply1Id());
                reply1VoList.get(loc).getReplyVoList().add(reply2Vo);
                Integer num=reply1VoList.get(loc).getReplyNum();
                reply1VoList.get(loc).setReplyNum(num+1);
            }
        }
        return reply1VoList;
    }

    @Override
    public void removeReplyByPostIds(List<Long> asList) {
        for (Long id:
             asList) {
            this.removeReplyByPostId(id);
        }
    }

    public void removeReplyByPostId(Long id) {
        List<ReplyEntity> replyEntityList = this.list(new QueryWrapper<ReplyEntity>().eq("post_id", id));
        for (ReplyEntity reply:
                replyEntityList) {
            reply.setIsDeleted(1);
            replyLikesService.removeByReplyId(reply.getReplyId());
        }
        this.updateBatchById(replyEntityList);
    }

    @Override
    public void logicRemoveByIds(List<Long> asList) {

    }

    /**
     * 根据传入的item来对应逻辑删除该item以及与该item在其他表有关系的一些数据
     * @param item
     */
    @Override
    @CacheEvict(value="replyCache",key = "'getFirstLevelComments'+ #item.postId")
    public void logicRemoveReply(ReplyEntity item) {
        item.setIsDeleted(1);
        this.updateById(item);
        this.logicRemoveReplyByReply1Id(item.getId());
        this.logicRemoveReplyByReplyId(item.getId());
        postService.removeReply(item.getPostId());
    }

    private void logicRemoveReplyByReplyId(Long replyId) {
        List<ReplyEntity> replyEntities = this.list(new QueryWrapper<ReplyEntity>().eq("reply_id", replyId));
        List<ReplyEntity> collect = replyEntities.stream().map(item -> {
            item.setIsDeleted(1);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }

    private void logicRemoveReplyByReply1Id(Long replyId) {
        List<ReplyEntity> replyEntities = this.list(new QueryWrapper<ReplyEntity>().eq("reply1_id", replyId));
        List<ReplyEntity> collect = replyEntities.stream().map(item -> {
            item.setIsDeleted(1);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }

    private Reply2Vo buildReply2(ReplyEntity item) throws ExecutionException, InterruptedException {
        Reply2Vo reply2Vo = new Reply2Vo();
        BeanUtils.copyProperties(item,reply2Vo);

        CompletableFuture<Void>userFuture=CompletableFuture.runAsync(()->{
            UserTo user = userFeignService.userInfo(item.getUserId());
            reply2Vo.setHeader(user.getHeader());
            reply2Vo.setUserNickname(user.getNickname());
        },threadPoolExecutor);

        CompletableFuture<Void>targetUserFuture=CompletableFuture.runAsync(()->{
            UserTo targetUser = userFeignService.userInfo(item.getTargetId());
            reply2Vo.setTargetNickname(targetUser.getNickname());
        },threadPoolExecutor);

        CompletableFuture.allOf(userFuture,targetUserFuture).get();
        return reply2Vo;
    }

    private Reply1Vo buildReply1(ReplyEntity item) throws ExecutionException, InterruptedException {
        Reply1Vo reply1Vo = new Reply1Vo();
        BeanUtils.copyProperties(item,reply1Vo);
        /**
         * 设置回复的用户信息
         */
        CompletableFuture<Void>userFuture=CompletableFuture.runAsync(()->{
            UserTo user = userFeignService.userInfo(item.getUserId());
            reply1Vo.setHeader(user.getHeader());
            reply1Vo.setUserNickname(user.getNickname());
        },threadPoolExecutor);

        CompletableFuture<Void>targetUserFuture=CompletableFuture.runAsync(()->{
            UserTo targetUser = userFeignService.userInfo(item.getTargetId());
            reply1Vo.setTargetNickname(targetUser.getNickname());
        },threadPoolExecutor);

        CompletableFuture<Void>replyListFuture=CompletableFuture.runAsync(()->{
            List<Reply2Vo>reply2VoList=new ArrayList<>();
            reply1Vo.setReplyVoList(reply2VoList);
            reply1Vo.setReplyNum(0);
        },threadPoolExecutor);

        CompletableFuture.allOf(userFuture,targetUserFuture,replyListFuture).get();
        return reply1Vo;
    }

}
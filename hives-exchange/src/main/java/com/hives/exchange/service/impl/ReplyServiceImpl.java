package com.hives.exchange.service.impl;

import com.hives.common.to.UserTo;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.exchange.feign.UserFeignService;
import com.hives.exchange.vo.Reply1Vo;
import com.hives.exchange.vo.Reply1Vo;
import com.hives.exchange.vo.Reply2Vo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReplyEntity> page = this.page(
                new Query<ReplyEntity>().getPage(params),
                new QueryWrapper<ReplyEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveReply(ReplyEntity reply) {
        reply.setCreateTime(new Date());
        this.save(reply);
    }

    @Override
    @Cacheable(value = "replyCache",key = "#root.method.name+ #postId")
    public List<Reply1Vo> getFirstLevelComments(Long postId) {
        List<Reply1Vo> reply1VoList=new ArrayList<>();
        List<ReplyEntity> replyEntityList = this.list(new QueryWrapper<ReplyEntity>().eq("post_id", postId));
        Map<Long,Integer>Reply1LocMap=new HashMap<>();
        for (ReplyEntity item:replyEntityList){
            if(item.getReply1Id()==0){
                Reply1Vo reply1Vo = buildReply1(item);
                reply1VoList.add(reply1Vo);
                Reply1LocMap.put(item.getId(),reply1VoList.size()-1);
            }else{
                Reply2Vo reply2Vo=buildReply2(item);
                Integer loc=Reply1LocMap.get(item.getReply1Id());
                reply1VoList.get(loc).getReplyVoList().add(reply2Vo);

            }
        }
        return reply1VoList;
    }

    private Reply2Vo buildReply2(ReplyEntity item) {
        Reply2Vo reply2Vo = new Reply2Vo();
        BeanUtils.copyProperties(item,reply2Vo);
        UserTo user = userFeignService.userInfo(item.getUserId());
        reply2Vo.setHeader(user.getHeader());
        reply2Vo.setUserNickname(user.getNickname());

        UserTo targetUser = userFeignService.userInfo(item.getTargetId());
        reply2Vo.setTargetNickname(targetUser.getNickname());

        return reply2Vo;
    }

    private Reply1Vo buildReply1(ReplyEntity item) {
        Reply1Vo reply1Vo = new Reply1Vo();
        BeanUtils.copyProperties(item,reply1Vo);
        /**
         * 设置回复的用户信息
         */
        UserTo user = userFeignService.userInfo(item.getUserId());
        reply1Vo.setHeader(user.getHeader());
        reply1Vo.setUserNickname(user.getNickname());

        UserTo targetUser = userFeignService.userInfo(item.getTargetId());
        reply1Vo.setTargetNickname(targetUser.getNickname());

        List<Reply2Vo>reply2VoList=new ArrayList<>();
        reply1Vo.setReplyVoList(reply2VoList);

        reply1Vo.setReplyNum(0);

        return reply1Vo;
    }

}
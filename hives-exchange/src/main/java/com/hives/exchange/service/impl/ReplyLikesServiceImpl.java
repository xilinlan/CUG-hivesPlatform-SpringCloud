package com.hives.exchange.service.impl;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.exchange.dao.ReplyLikesDao;
import com.hives.exchange.entity.ReplyLikesEntity;
import com.hives.exchange.service.ReplyLikesService;


@Service("replyLikesService")
public class ReplyLikesServiceImpl extends ServiceImpl<ReplyLikesDao, ReplyLikesEntity> implements ReplyLikesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReplyLikesEntity> page = this.page(
                new Query<ReplyLikesEntity>().getPage(params),
                new QueryWrapper<ReplyLikesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void removeByReplyId(Long replyId) {
        List<ReplyLikesEntity> replyLikesEntities = this.list(new QueryWrapper<ReplyLikesEntity>().eq("reply_id", replyId));
        List<ReplyLikesEntity> collect = replyLikesEntities.stream().map(item -> {
            item.setIsDeleted(1);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }

}
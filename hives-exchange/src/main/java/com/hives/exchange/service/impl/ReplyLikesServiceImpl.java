package com.hives.exchange.service.impl;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
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

}
package com.hives.exchange.service.impl;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.exchange.dao.PostLikesDao;
import com.hives.exchange.entity.PostLikesEntity;
import com.hives.exchange.service.PostLikesService;


@Service("postLikesService")
public class PostLikesServiceImpl extends ServiceImpl<PostLikesDao, PostLikesEntity> implements PostLikesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PostLikesEntity> page = this.page(
                new Query<PostLikesEntity>().getPage(params),
                new QueryWrapper<PostLikesEntity>()
        );

        return new PageUtils(page);
    }

}
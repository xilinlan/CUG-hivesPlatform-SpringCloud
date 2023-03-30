package com.hives.exchange.service.impl;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.exchange.dao.PostImagesDao;
import com.hives.exchange.entity.PostImagesEntity;
import com.hives.exchange.service.PostImagesService;


@Service("postImagesService")
public class PostImagesServiceImpl extends ServiceImpl<PostImagesDao, PostImagesEntity> implements PostImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PostImagesEntity> page = this.page(
                new Query<PostImagesEntity>().getPage(params),
                new QueryWrapper<PostImagesEntity>()
        );

        return new PageUtils(page);
    }

}
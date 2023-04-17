package com.hives.exchange.service.impl;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.hives.exchange.dao.PostCollectsDao;
import com.hives.exchange.entity.PostCollectsEntity;
import com.hives.exchange.service.PostCollectsService;


@Service("postCollectsService")
public class PostCollectsServiceImpl extends ServiceImpl<PostCollectsDao, PostCollectsEntity> implements PostCollectsService {

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

}
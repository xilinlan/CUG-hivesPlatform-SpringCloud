package com.hives.exchange.service.impl;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.Query;
import com.hives.exchange.entity.PostEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<String> getImages(Long id) {
        List<PostImagesEntity> postImagesEntityList = this.list(new QueryWrapper<PostImagesEntity>().eq("post_id", id).eq("is_deleted",0));
        List<String> collect = postImagesEntityList.stream().map(item -> item.getUrl()).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void removeImagesByPostId(Long postId) {
        List<PostImagesEntity> imagesEntityList = this.list(new QueryWrapper<PostImagesEntity>().eq("post_id", postId));
        List<PostImagesEntity> collect = imagesEntityList.stream().map(item -> {
            item.setIsDeleted(1);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }

}
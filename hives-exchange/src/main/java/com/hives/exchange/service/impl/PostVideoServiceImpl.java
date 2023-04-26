package com.hives.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hives.exchange.dao.PostImagesDao;
import com.hives.exchange.dao.PostVideoDao;
import com.hives.exchange.entity.PostImagesEntity;
import com.hives.exchange.entity.PostVideoEntity;
import com.hives.exchange.service.PostImagesService;
import com.hives.exchange.service.PostVideoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/24/14:31
 * @Description:
 */
@Service("postVideoService")
public class PostVideoServiceImpl extends ServiceImpl<PostVideoDao, PostVideoEntity> implements PostVideoService {
    @Override
    public void removeVideoByPostId(Long postId) {
        List<PostVideoEntity> videoEntityList = this.list(new QueryWrapper<PostVideoEntity>().eq("post_id", postId));
        List<PostVideoEntity> collect = videoEntityList.stream().map(item -> {
            item.setIsDeleted(1);
            return item;
        }).collect(Collectors.toList());
        this.updateBatchById(collect);
    }

    @Override
    public List<String> getVideos(Long postId) {
        List<PostVideoEntity> list = this.list(new QueryWrapper<PostVideoEntity>().eq("post_id", postId).eq("is_deleted", 0));
        List<String> collect = list.stream().map(item -> item.getUrl()).collect(Collectors.toList());
        return collect;
    }
}

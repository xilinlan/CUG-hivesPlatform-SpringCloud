package com.hives.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.exchange.entity.PostVideoEntity;

import java.util.List;


/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/24/14:30
 * @Description:
 */
public interface PostVideoService extends IService<PostVideoEntity> {
    void removeVideoByPostId(Long postId);

    List<String> getVideos(Long postId);
}

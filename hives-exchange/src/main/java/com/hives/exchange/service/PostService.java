package com.hives.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.common.utils.PageUtils;
import com.hives.exchange.dto.PostDto;
import com.hives.exchange.entity.PostEntity;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * 贴子
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
public interface PostService extends IService<PostEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void savePost(Long userId,PostDto post);

    PageUtils queryPostPage(Map<String, Object> params,Long userId) throws ExecutionException, InterruptedException;

    void cacheTest(Long userId);
}


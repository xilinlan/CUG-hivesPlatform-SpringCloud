package com.hives.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.common.utils.PageUtils;
import com.hives.exchange.entity.PostCollectsEntity;

import java.util.Map;

/**
 * 
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
public interface PostCollectsService extends IService<PostCollectsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PostCollectsEntity isCollect(Long userId, Long id);

    void updateCollects(Long userId, Long postId);
}


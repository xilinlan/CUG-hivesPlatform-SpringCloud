package com.hives.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.common.utils.PageUtils;
import com.hives.exchange.entity.PostImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:32
 */
public interface PostImagesService extends IService<PostImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<String> getImages(Long id);
}


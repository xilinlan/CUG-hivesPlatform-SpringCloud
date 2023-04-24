package com.hives.exchange.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hives.exchange.entity.PostImagesEntity;
import com.hives.exchange.entity.PostVideoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/24/14:27
 * @Description:
 */
@Mapper
public interface PostVideoDao extends BaseMapper<PostVideoEntity> {
}

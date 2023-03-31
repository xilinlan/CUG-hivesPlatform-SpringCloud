package com.hives.exchange.dao;

import com.hives.exchange.entity.PostEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 贴子
 * 
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
@Mapper
public interface PostDao extends BaseMapper<PostEntity> {
	
}

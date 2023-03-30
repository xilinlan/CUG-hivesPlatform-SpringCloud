package com.hives.user.dao;

import com.hives.user.entity.AdminEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员
 * 
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:10:36
 */
@Mapper
public interface AdminDao extends BaseMapper<AdminEntity> {
	
}

package com.hives.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.common.utils.PageUtils;
import com.hives.user.entity.AdminEntity;

import java.util.Map;

/**
 * 管理员
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:10:36
 */
public interface AdminService extends IService<AdminEntity> {

    /**
     * 管理员名单分页查询
     * @param params
     * @return PageUtils
     */
    PageUtils queryPage(Map<String, Object> params);
}


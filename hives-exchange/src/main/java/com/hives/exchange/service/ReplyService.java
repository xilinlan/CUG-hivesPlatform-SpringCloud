package com.hives.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.common.utils.PageUtils;
import com.hives.exchange.entity.ReplyEntity;
import com.hives.exchange.vo.Reply1Vo;

import java.util.List;
import java.util.Map;

/**
 * 回答
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
public interface ReplyService extends IService<ReplyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveReply(ReplyEntity reply);

    List<Reply1Vo> getFirstLevelComments(Long postId);
}


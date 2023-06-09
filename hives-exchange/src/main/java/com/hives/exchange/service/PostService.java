package com.hives.exchange.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hives.common.utils.PageUtils;
import com.hives.exchange.dto.PostDto;
import com.hives.exchange.entity.PostEntity;
import com.hives.exchange.entity.ReplyEntity;
import com.hives.exchange.vo.PostVo;

import java.util.List;
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

    List<PostVo> getPostVoList(Long userId,List<PostEntity> postList);

    PageUtils queryOwnPage(Map<String, Object> params, Long userId);

    void logicRemoveByIds(List<Long> asList);

    void updatePostUpdateTime(Long postId);

    void removeReply(Long postId);

    PageUtils getFollowPost(Map<String, Object> params , Long userId);
}


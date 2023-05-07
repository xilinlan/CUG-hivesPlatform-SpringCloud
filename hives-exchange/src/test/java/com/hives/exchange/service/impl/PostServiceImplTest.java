package com.hives.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hives.common.constant.PostConstant;
import com.hives.common.utils.PageUtils;
import com.hives.exchange.dto.PostDto;
import com.hives.exchange.entity.PostEntity;
import com.hives.exchange.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/05/06/21:42
 * @Description:
 */
@SpringBootTest
class PostServiceImplTest {
    @Autowired
    private PostService postService;

    @Test
    @Transactional
    void savePost() {
        PostDto post=new PostDto();
        post.setContent("你好");
        post.setUserId(3L);
        post.setType(PostConstant.PostTypeEnum.COMMON.getCode());
        post.setUpdateTime(new Date());
        post.setUrls(new ArrayList<>());
        postService.savePost(post.getUserId(),post);
    }

    @Test
    void queryPostPage() throws ExecutionException, InterruptedException {
        Map<String,Object>map=new HashMap<>();
        map.put("page","1");
        map.put("limit","10");
        PageUtils pageUtils = postService.queryPostPage(map, 3L);
        assertNotNull(pageUtils.getList());
    }


    @Test
    void queryOwnPage() {
        Map<String,Object>map=new HashMap<>();
        map.put("page","1");
        map.put("limit","10");
        PageUtils pageUtils = postService.queryOwnPage(map, 3L);
        assertNotNull(pageUtils.getList());
    }

    @Test
    @Transactional
    void logicRemoveByIds() {
        List<Long>list=new ArrayList<>();
        list.add(7L);
        postService.logicRemoveByIds(list);
        PostEntity post = postService.getOne(new QueryWrapper<PostEntity>().eq("id", 7L).eq("is_deleted", 0));
        assertNull(post);
    }

    @Test
    @Transactional
    void updatePostUpdateTime() {
        PostEntity post1 = postService.getById(6L);
        String oldTime = post1.getUpdateTime().toString();
        PostEntity post2 = postService.getById(6L);
        String newTime = post2.getUpdateTime().toString();
        System.out.println(oldTime);
        System.out.println(newTime);
        assertNotSame(newTime,oldTime);
    }

    @Test
    @Transactional
    void removeReply() {
        PostEntity post1 = postService.getById(7L);
        Long oldReply = post1.getReply();

        postService.removeReply(7L);

        PostEntity post2 = postService.getById(7L);
        Long newReply = post2.getReply();

        assertNotEquals(oldReply,newReply);
    }

    @Test
    void getFollowPost() {
        Map<String,Object>map=new HashMap<>();
        map.put("page","1");
        map.put("limit","10");
        PageUtils pageUtils = postService.getFollowPost(map, 3L);
        assertNotNull(pageUtils.getList());
    }
}
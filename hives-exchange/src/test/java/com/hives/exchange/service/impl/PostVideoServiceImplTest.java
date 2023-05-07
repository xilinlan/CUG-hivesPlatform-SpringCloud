package com.hives.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hives.exchange.entity.PostVideoEntity;
import com.hives.exchange.service.PostVideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/05/06/21:42
 * @Description:
 */
@SpringBootTest
class PostVideoServiceImplTest {
    @Autowired
    private PostVideoService postVideoService;

    @Test
    @Transactional
    void removeVideoByPostId() {
        postVideoService.removeVideoByPostId(38L);
        List<PostVideoEntity> list = postVideoService.list(new QueryWrapper<PostVideoEntity>().eq("post_id", 38L));
        for (PostVideoEntity post:
             list) {
            assertEquals(1,post.getIsDeleted());
        }
    }

    @Test
    void getVideos() {
        List<String> videos = postVideoService.getVideos(38L);
        assertNotNull(videos);
    }
}
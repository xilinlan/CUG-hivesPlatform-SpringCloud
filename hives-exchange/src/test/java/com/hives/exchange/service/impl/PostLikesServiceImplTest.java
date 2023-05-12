package com.hives.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hives.exchange.entity.PostLikesEntity;
import com.hives.exchange.service.PostLikesService;
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
 * @Date: 2023/05/06/21:41
 * @Description:
 */
@SpringBootTest
class PostLikesServiceImplTest {
    @Autowired
    private PostLikesService postLikesService;


    @Test
    @Transactional
    public void updatePostLikes() {
        PostLikesEntity like1 = postLikesService.isLike(3L, 9L);
        assertNull(like1);

        postLikesService.updatePostLikes(3L,45L);
        PostLikesEntity like2 = postLikesService.isLike(3L, 7L);
        assertNull(like2);

        postLikesService.updatePostLikes(3L,7L);
        PostLikesEntity like3 = postLikesService.isLike(3L, 7L);
        assertNotNull(like3);
    }

    @Test
    @Transactional
    public void removePostLikesByPostId() {
        postLikesService.removePostLikesByPostId(7L);
        List<PostLikesEntity> list = postLikesService.list(new QueryWrapper<PostLikesEntity>().eq("post_id", 7L));
        for (PostLikesEntity like:
             list) {
            assertEquals(like.getIsDeleted(),1);
        }
        PostLikesEntity postLikes = postLikesService.getById(1L);
        System.out.println(postLikes);
        postLikes.setIsDeleted(0);
        postLikesService.updateById(postLikes);
    }
}
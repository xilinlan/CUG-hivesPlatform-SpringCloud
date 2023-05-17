package com.hives.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hives.common.utils.PageUtils;
import com.hives.exchange.entity.PostCollectsEntity;
import com.hives.exchange.service.PostCollectsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/05/06/21:40
 * @Description:
 */
@SpringBootTest
@Transactional
class PostCollectsServiceImplTest {
    @Autowired
    private PostCollectsService postCollectsService;

    @Test
    public void isCollect() {
        PostCollectsEntity collect1 = postCollectsService.isCollect(3L, 45L);
        assertNotNull(collect1);

        PostCollectsEntity collect2 = postCollectsService.isCollect(3L, 8L);
        assertNull(collect2);

        PostCollectsEntity collect3 = postCollectsService.isCollect(3L, 12L);
        assertNull(collect3);
    }

    @Test
    @Transactional
    public void updateCollects() {
        PostCollectsEntity collect=postCollectsService.getById(1L);
        PostCollectsEntity collect2=new PostCollectsEntity();
        BeanUtils.copyProperties(collect,collect2);
        collect.setPostId(8L);
        postCollectsService.updateById(collect);
        collect=postCollectsService.getById(1L);
        assertNotEquals(collect.getPostId(),collect2.getPostId());
        collect2.setPostId(8L);
        assertEquals(collect.getPostId(),collect2.getPostId());
    }

    @Test
    void getUserCollects() {
        Map<String,Object>map=new HashMap<>();
        map.put("page","1");
        map.put("limit","10");
        PageUtils userCollects = postCollectsService.getUserCollects(map, 3L);
        System.out.println(userCollects.getList());
        assertNotNull(userCollects.getList());
    }

    @Test
    void removePostCollectsByPostId() {
        postCollectsService.removePostCollectsByPostId(7L);
        List<PostCollectsEntity> list = postCollectsService.list(new QueryWrapper<PostCollectsEntity>().eq("post_id", 7));
        for (PostCollectsEntity collect:
             list) {
            assertEquals(collect.getIsDeleted(),1);
        }
        PostCollectsEntity postCollects = postCollectsService.getById(1L);
        postCollects.setIsDeleted(0);
        postCollectsService.updateById(postCollects);
    }
}
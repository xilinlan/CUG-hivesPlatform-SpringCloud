package com.hives.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hives.exchange.entity.ReplyEntity;
import com.hives.exchange.entity.ReplyLikesEntity;
import com.hives.exchange.service.ReplyLikesService;
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
class ReplyLikesServiceImplTest {
    @Autowired
    private ReplyLikesService replyLikesService;

    @Test
    @Transactional
    void removeByReplyId() {
        replyLikesService.removeByReplyId(3L);
        List<ReplyLikesEntity> list = replyLikesService.list(new QueryWrapper<ReplyLikesEntity>().eq("reply_id", 3L));
        for (ReplyLikesEntity reply:
             list) {
            assertEquals(1,reply.getIsDeleted());
        }
    }
}
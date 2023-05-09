package com.hives.exchange.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hives.exchange.entity.ReplyEntity;
import com.hives.exchange.service.ReplyService;
import com.hives.exchange.vo.Reply1Vo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
class ReplyServiceImplTest {
    @Autowired
    private ReplyService replyService;

    @Test
    @Transactional
    void saveReply() {
        ReplyEntity replyEntity=new ReplyEntity();
        replyEntity.setPostId(7L);
        replyEntity.setUserId(3L);
        replyEntity.setTargetId(4L);
        replyEntity.setContent("测试内容");
        replyService.saveReply(replyEntity);
    }

    @Test
    void getFirstLevelComments() throws ExecutionException, InterruptedException {
        List<Reply1Vo> firstLevelComments = replyService.getFirstLevelComments(7L);
        assertNotNull(firstLevelComments);
        assertNotEquals(0,firstLevelComments.size());
        List<Reply1Vo> firstLevelComments1 = replyService.getFirstLevelComments(8L);
        assertEquals(0,firstLevelComments1.size());
    }

    @Test
    @Transactional
    void removeReplyByPostIds() {
        List<Long>list=new ArrayList<>();
        list.add(7L);
        replyService.removeReplyByPostIds(list);
        List<ReplyEntity> list1 = replyService.list(new QueryWrapper<ReplyEntity>().eq("post_id", 7L));
        for (ReplyEntity reply:
             list1) {
            assertEquals(1,reply.getIsDeleted());
        }
    }

}
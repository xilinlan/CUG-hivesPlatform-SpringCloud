package com.hives.exchange.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/19/12:54
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply2Vo {
    /**
     * id
     */
    private Long id;
    /**
     * 贴子ID
     */
    private Long postId;
    /**
     * 被回复的回复id
     */
    private Long replyId;
    /**
     * 回复内容
     */
    private String content;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 喜欢
     */
    private Long likes;
    /**
     * 回复人的用户id
     */
    private Long userId;
    /**
     * 被回复人的用户id
     */
    private Long targetId;
    /**
     * 头像
     */
    private String header;
    /**
     * 用户昵称
     */
    private String userNickname;
    /**
     * 被回复用户昵称
     */
    private String targetNickname;
}

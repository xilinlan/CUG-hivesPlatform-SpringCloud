package com.hives.exchange.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/19/12:42
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reply1Vo {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    /**
     * 回复数量
     */
    private Integer replyNum;

    /**
     * 二级回复表
     */
    private List<Reply2Vo>replyVoList;
}

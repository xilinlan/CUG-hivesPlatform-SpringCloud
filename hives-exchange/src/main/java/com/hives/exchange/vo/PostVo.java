package com.hives.exchange.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/14/15:04
 * @Description:
 */
@Data
public class PostVo {
    /**
     * 帖子id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 头像
     */
    private String header;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 链接
     */
    private List<String> urls;
    /**
     * 喜欢
     */
    private Long likes;
    /**
     * 回复
     */
    private Long reply;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 收藏
     */
    private Long collects;
    /**
     * 热度
     */
    private Long hot;
    /**
     * 是否收藏
     */
    private Boolean isCollect;
    /**
     * 是否喜欢
     */
    private Boolean isLove;
}

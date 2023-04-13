package com.hives.exchange.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/13/15:30
 * @Description:发帖前端接收对象
 */
@Data
public class PostDto{
    private Long id;
    /**
     * 内容
     */
    private String content;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 修改日期
     */
    private Date updateTime;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 浏览量
     */
    private Long visits;
    /**
     * 点赞数
     */
    private Long likes;
    /**
     * 回复数
     */
    private Long reply;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 是否显示[0-未被删除，1被删除]
     */
    private Integer isDeleted;

    List<String> urls;
}

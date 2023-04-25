package com.hives.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/24/14:25
 * @Description:
 */
@Data
@TableName("tb_post_video")
public class PostVideoEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 贴子ID
     */
    private Long postId;
    /**
     * 图片路径
     */
    private String url;
    /**
     * 是否删除
     */
    private Integer isDeleted;
}

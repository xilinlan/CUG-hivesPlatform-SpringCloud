package com.hives.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 回答
 * 
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
@Data
@TableName("tb_reply")
public class ReplyEntity implements Serializable {
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
	 * 是否显示[0-未被删除，1被删除]
	 */
	private Integer isDeleted;

	/**
	 * 所属的一级回复id
	 */
	private Long reply1Id;

}

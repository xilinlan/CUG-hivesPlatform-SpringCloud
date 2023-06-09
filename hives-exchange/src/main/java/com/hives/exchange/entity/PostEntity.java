package com.hives.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 贴子
 * 
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
@Data
@TableName("tb_post")
public class PostEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 修改日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 收藏量
	 */
	private Long collects;
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

}

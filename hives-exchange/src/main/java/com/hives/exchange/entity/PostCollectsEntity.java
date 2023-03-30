package com.hives.exchange.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
@Data
@TableName("tb_post_collects")
public class PostCollectsEntity implements Serializable {
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
	 * 用户ID
	 */
	private Long userId;
	/**
	 * 是否显示[0-未被删除，1被删除]
	 */
	private Integer isDeleted;

}

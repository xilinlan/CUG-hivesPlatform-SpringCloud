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
 * @date 2023-03-30 14:19:32
 */
@Data
@TableName("tb_post_images")
public class PostImagesEntity implements Serializable {
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
	 * 排序
	 */
	private Integer sort;

}

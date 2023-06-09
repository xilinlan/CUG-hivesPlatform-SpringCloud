package com.hives.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 用户
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:10:36
 */
@Data
@TableName("tb_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;

	/**
	 * 手机号码
	 */
	private String phoneNumber;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 会员等级
	 */
	private Integer level;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 头像
	 */
	private String header;
	/**
	 * 主页背景
	 */
	private String background;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 生日
	 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	/**
	 * 注册日期
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**
	 * 最后活跃
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastTime;
	/**
	 * 粉丝数
	 */
	private Integer fansCount;
	/**
	 * 关注数
	 */
	private Integer followCount;


}

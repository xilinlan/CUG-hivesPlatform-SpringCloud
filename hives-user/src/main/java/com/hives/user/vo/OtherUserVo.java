package com.hives.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 其他用户的信息以及是否关注
 * @author xilinlan
 */
@Data
public class OtherUserVo {

        /**
         * id
         */
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
        /**
         * 是否关注
         */
        private Boolean isFollow;
}

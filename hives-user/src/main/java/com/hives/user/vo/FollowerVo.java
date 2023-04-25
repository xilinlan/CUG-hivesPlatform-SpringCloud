package com.hives.user.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: xilinlan
 * @Date: 2023/04/24/15:48
 * @Description:
 */
@Data
public class FollowerVo {

    /**
     * id
     */
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 被关注用户ID
     */
    private Long targetId;
    /**
     * 对方昵称
     */
    private String nickname;
    /**
     * 对方头像
     */
    private String header;
}

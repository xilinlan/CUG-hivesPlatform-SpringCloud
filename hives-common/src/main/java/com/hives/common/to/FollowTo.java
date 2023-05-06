package com.hives.common.to;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/25/20:33
 * @Description:
 */
public class FollowTo {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 被关注用户ID
     */
    private Long targetId;

    public FollowTo() {
    }

    public FollowTo(Long userId, Long targetId) {
        this.userId = userId;
        this.targetId = targetId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}

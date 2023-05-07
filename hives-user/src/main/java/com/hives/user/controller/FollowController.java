package com.hives.user.controller;

import java.util.List;
import java.util.Map;

import com.hives.common.to.FollowTo;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import com.hives.user.vo.FollowerVo;
import com.hives.user.vo.OtherUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hives.user.entity.FollowEntity;
import com.hives.user.service.FollowService;




/**
 *
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:10:36
 */
@RestController
@RequestMapping("user/follow")
public class FollowController {
    @Autowired
    private FollowService followService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = followService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/getFollows")
    public R getFollow(@RequestParam("userId") Long userId){
        List<FollowerVo> follow = followService.getFollow(userId);
        return R.ok().put("follow", follow);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		FollowEntity follow = followService.getById(id);

        return R.ok().put("follow", follow);
    }

    @GetMapping("/getOtherUserInfo")
    public R getOtherUserInfo(@RequestParam("userId") Long userId, @RequestParam("targetId") Long targetId){
        OtherUserVo otherUserInfo = followService.getOtherUserInfo(userId, targetId);
        return R.ok().put("otherUserInfo", otherUserInfo);
    }
    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody FollowEntity follow){
        followService.saveFollow(follow);
        return R.ok();
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody FollowEntity follow){
		followService.updateById(follow);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public R delete(@RequestBody FollowEntity follow){
		followService.deleteFollow(follow);

        return R.ok();
    }

    @GetMapping("/getFromOtherService/{userId}")
    public List<FollowTo> getFollowFromOtherService(@PathVariable("userId")Long userId){
        List<FollowTo>followToList=followService.getFollowTo(userId);
        return followToList;
    }

}

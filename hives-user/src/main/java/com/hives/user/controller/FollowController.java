package com.hives.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import com.hives.user.vo.FollowerVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    //@RequiresPermissions("user:follow:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = followService.queryPage(params);

        return R.ok().put("page", page);
    }

    @GetMapping("/getFollows")
    //@RequiresPermissions("user:follow:list")
    public R getFollow(@RequestParam("userId") Long userId){
        List<FollowerVo> follow = followService.getFollow(userId);
        return R.ok().put("follow", follow);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("user:follow:info")
    public R info(@PathVariable("id") Long id){
		FollowEntity follow = followService.getById(id);

        return R.ok().put("follow", follow);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("user:follow:save")
    public R save(@RequestBody FollowEntity follow){
		followService.save(follow);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("user:follow:update")
    public R update(@RequestBody FollowEntity follow){
		followService.updateById(follow);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("user:follow:delete")
    public R delete(@RequestBody Long[] ids){
		followService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

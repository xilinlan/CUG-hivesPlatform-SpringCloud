package com.hives.exchange.controller;

import java.util.Arrays;
import java.util.Map;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hives.exchange.entity.PostLikesEntity;
import com.hives.exchange.service.PostLikesService;




/**
 * 
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
@RestController
@RequestMapping("exchange/postlikes")
public class PostLikesController {
    @Autowired
    private PostLikesService postLikesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("exchange:postlikes:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = postLikesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("exchange:postlikes:info")
    public R info(@PathVariable("id") Long id){
		PostLikesEntity postLikes = postLikesService.getById(id);

        return R.ok().put("postLikes", postLikes);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("exchange:postlikes:save")
    public R save(@RequestBody PostLikesEntity postLikes){
		postLikesService.save(postLikes);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("exchange:postlikes:update")
    public R update(@RequestBody PostLikesEntity postLikes){
		// postLikesService.updateById(postLikes);
        postLikesService.updatePostLikes(postLikes.getUserId(),postLikes.getPostId());
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("exchange:postlikes:delete")
    public R delete(@RequestBody Long[] ids){
		postLikesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

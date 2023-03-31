package com.hives.exchange.controller;

import java.util.Arrays;
import java.util.Map;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hives.exchange.entity.ReplyLikesEntity;
import com.hives.exchange.service.ReplyLikesService;




/**
 * 
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
@RestController
@RequestMapping("exchange/replylikes")
public class ReplyLikesController {
    @Autowired
    private ReplyLikesService replyLikesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("exchange:replylikes:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = replyLikesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("exchange:replylikes:info")
    public R info(@PathVariable("id") Long id){
		ReplyLikesEntity replyLikes = replyLikesService.getById(id);

        return R.ok().put("replyLikes", replyLikes);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("exchange:replylikes:save")
    public R save(@RequestBody ReplyLikesEntity replyLikes){
		replyLikesService.save(replyLikes);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("exchange:replylikes:update")
    public R update(@RequestBody ReplyLikesEntity replyLikes){
		replyLikesService.updateById(replyLikes);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("exchange:replylikes:delete")
    public R delete(@RequestBody Long[] ids){
		replyLikesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

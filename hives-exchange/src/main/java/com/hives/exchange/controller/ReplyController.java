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

import com.hives.exchange.entity.ReplyEntity;
import com.hives.exchange.service.ReplyService;




/**
 * 回答
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
@RestController
@RequestMapping("exchange/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("exchange:reply:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = replyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("exchange:reply:info")
    public R info(@PathVariable("id") Long id){
		ReplyEntity reply = replyService.getById(id);

        return R.ok().put("reply", reply);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("exchange:reply:save")
    public R save(@RequestBody ReplyEntity reply){
		replyService.save(reply);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("exchange:reply:update")
    public R update(@RequestBody ReplyEntity reply){
		replyService.updateById(reply);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("exchange:reply:delete")
    public R delete(@RequestBody Long[] ids){
		replyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

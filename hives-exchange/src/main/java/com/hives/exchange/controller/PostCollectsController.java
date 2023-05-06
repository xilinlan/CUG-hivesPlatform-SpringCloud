package com.hives.exchange.controller;

import java.util.Arrays;
import java.util.Map;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hives.exchange.entity.PostCollectsEntity;
import com.hives.exchange.service.PostCollectsService;




/**
 * 
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
@RestController
@RequestMapping("exchange/postcollects")
public class PostCollectsController {
    @Autowired
    private PostCollectsService postCollectsService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        Long userId = Long.parseLong((String) params.get("userId"));
        PageUtils page= postCollectsService.getUserCollects(params,userId);
        return R.ok().put("page",page);
    }
    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		PostCollectsEntity postCollects = postCollectsService.getById(id);

        return R.ok().put("postCollects", postCollects);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody PostCollectsEntity postCollects){
		postCollectsService.save(postCollects);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody PostCollectsEntity postCollects){
		// postCollectsService.updateById(postCollects);
        postCollectsService.updateCollects(postCollects.getUserId(),postCollects.getPostId());
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("exchange:postcollects:delete")
    public R delete(@RequestBody Long[] ids){
		postCollectsService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

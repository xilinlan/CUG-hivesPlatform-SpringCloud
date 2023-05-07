package com.hives.exchange.controller;

import java.util.Arrays;
import java.util.Map;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hives.exchange.entity.PostImagesEntity;
import com.hives.exchange.service.PostImagesService;




/**
 * 
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:32
 */
@RestController
@RequestMapping("exchange/postimages")
public class PostImagesController {
    @Autowired
    private PostImagesService postImagesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("exchange:postimages:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = postImagesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("exchange:postimages:info")
    public R info(@PathVariable("id") Long id){
		PostImagesEntity postImages = postImagesService.getById(id);

        return R.ok().put("postImages", postImages);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("exchange:postimages:save")
    public R save(@RequestBody PostImagesEntity postImages){
		postImagesService.save(postImages);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("exchange:postimages:update")
    public R update(@RequestBody PostImagesEntity postImages){
		postImagesService.updateById(postImages);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("exchange:postimages:delete")
    public R delete(@RequestBody Long[] ids){
		postImagesService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

package com.hives.exchange.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.hives.common.constant.PostConstant;
import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import com.hives.exchange.dto.PostDto;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.hives.exchange.entity.PostEntity;
import com.hives.exchange.service.PostService;




/**
 * 贴子
 *
 * @author zhangtao
 * @email 2298805496@qq.com
 * @date 2023-03-30 14:19:31
 */
@RestController
@RequestMapping("exchange/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Value("${person.name}")
    private String name;

    @Value("${person.age}")
    private Integer age;

    @RequestMapping("/test")
    public R test(){
        return R.ok().put("name",name).put("age",age);
    }

    /**
     * 获取首页贴子，按PageUtils分页返回
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) throws ExecutionException, InterruptedException {
        Long userId = Long.parseLong((String) params.get("userId"));
        PageUtils page =postService.queryPostPage(params,userId);

        return R.ok().put("page", page);
    }

    /**
     * 获取个人发帖信息
     * @param params
     * @return
     */
    @RequestMapping("/own")
    public R ownPostList(@RequestParam Map<String, Object> params){
        Long userId = Long.parseLong((String) params.get("userId"));

        PageUtils page=postService.queryOwnPage(params,userId);
        return R.ok().put("page",page);
    }


    /**
     * ；信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("exchange:post:info")
    public R info(@PathVariable("id") Long id){
		PostEntity post = postService.getById(id);

        return R.ok().put("post", post);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PostDto post){
        postService.savePost(post.getUserId(), post);
        return R.ok().put("postStatus", PostConstant.PostEnum.SUCCESS.getCode()).put("msg",PostConstant.PostEnum.SUCCESS.getMsg());
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody PostEntity post){
		postService.updateById(post);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        // 批量逻辑删除帖子，可以传任意数量的帖子
        postService.logicRemoveByIds(Arrays.asList(ids));
        return R.ok();
    }

}

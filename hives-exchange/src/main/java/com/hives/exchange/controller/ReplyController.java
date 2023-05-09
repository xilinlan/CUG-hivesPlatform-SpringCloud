package com.hives.exchange.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.hives.common.utils.PageUtils;
import com.hives.common.utils.R;
import com.hives.exchange.vo.Reply1Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/firstLevelComments")
    public R firstLevelComments(@RequestParam Long postId) throws ExecutionException, InterruptedException {
        List<Reply1Vo> replyVoList=replyService.getFirstLevelComments(postId);
        return R.ok().put("data",replyVoList);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = replyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ReplyEntity reply = replyService.getById(id);

        return R.ok().put("reply", reply);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ReplyEntity reply){
        ReplyEntity replyEntity = replyService.saveReply(reply);
        return R.ok().put("reply",replyEntity);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ReplyEntity reply){
		replyService.updateById(reply);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @Transactional
    public R delete(@RequestBody Long[] ids){
        List<ReplyEntity> replyEntityList = replyService.listByIds(Arrays.asList(ids));
        for (ReplyEntity item:
                replyEntityList) {
            replyService.logicRemoveReply(item);
        }
        return R.ok();
    }

}

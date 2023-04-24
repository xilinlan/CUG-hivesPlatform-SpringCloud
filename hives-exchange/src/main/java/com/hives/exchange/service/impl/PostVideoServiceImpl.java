package com.hives.exchange.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hives.exchange.dao.PostImagesDao;
import com.hives.exchange.dao.PostVideoDao;
import com.hives.exchange.entity.PostImagesEntity;
import com.hives.exchange.entity.PostVideoEntity;
import com.hives.exchange.service.PostImagesService;
import com.hives.exchange.service.PostVideoService;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/24/14:31
 * @Description:
 */
@Service("postVideoService")
public class PostVideoServiceImpl extends ServiceImpl<PostVideoDao, PostVideoEntity> implements PostVideoService {
}

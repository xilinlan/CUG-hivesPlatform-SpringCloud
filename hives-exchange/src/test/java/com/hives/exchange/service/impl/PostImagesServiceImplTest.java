package com.hives.exchange.service.impl;

import com.hives.exchange.service.PostImagesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/05/06/21:41
 * @Description:
 */
@SpringBootTest
class PostImagesServiceImplTest {
    @Autowired
    private PostImagesService postImagesService;


    @Test
    @Transactional
    void postImagesTest() {
        postImagesService.removeImagesByPostId(7L);
        List<String> images = postImagesService.getImages(7L);
        System.out.println(images);
        assertEquals(0,images.size());
    }
}
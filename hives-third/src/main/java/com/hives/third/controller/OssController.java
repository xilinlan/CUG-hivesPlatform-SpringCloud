package com.hives.third.controller;

import com.hives.common.utils.R;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OssController {

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.access-key}")
    private String accessKey;

    @Value("${qiniu.secret-key}")
    private String secretKey;


    @RequestMapping("/oss/policy")
    public R policy(){
        Auth auth =Auth.create(accessKey,secretKey);

        String upToken = auth.uploadToken(bucket);

        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String dir = format+"/";

        Map<String, String> ma = new HashMap<>();
        ma.put("upToken", upToken);
        ma.put("dir",dir);
        return R.ok().put("data", ma);
    }
}

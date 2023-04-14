package com.hives.exchange.feign;

import com.hives.common.to.UserTo;
import com.hives.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/14/16:07
 * @Description:
 */
@FeignClient("hives-user-zt")
public interface UserFeignService {
    @GetMapping("/user/user/userInfo/{id}")
    UserTo userInfo(@PathVariable("id")Long id);

}

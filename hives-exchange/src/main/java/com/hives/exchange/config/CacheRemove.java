package com.hives.exchange.config;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: zhangtao
 * @Date: 2023/04/16/21:26
 * @Description:
 */
@Target({METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheRemove {
    String value() default "";
    String[] key() default {};
}

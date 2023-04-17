package com.hives.exchange.config;

import com.hives.exchange.config.CacheRemove;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Aspect
@Component
public class CacheRemoveAspect {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 意为在标注了CacheRemove的方法处触发Pointcut执行该类中定义的方法
     */
    @Pointcut(value = "(execution(* *.*(..)) && @annotation(com.hives.exchange.config.CacheRemove))")
    private void pointcut() {}

    @AfterReturning(value = "pointcut()")
    private void process(JoinPoint joinPoint){
        //获取切入方法的数据
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Object[] args = joinPoint.getArgs();

        //获取切入方法
        Method method = signature.getMethod();
        //获得注解
        CacheRemove cacheRemove = method.getAnnotation(CacheRemove.class);

        if (cacheRemove != null) {
            String[] keys = cacheRemove.key();
            for (String key : keys) {
                //指定清除的key的缓存
                cleanRedisCache("*" + key+ args[0] + "*");
            }
        }
    }

    private void cleanRedisCache(String key) {
        if (key != null) {
            Set<String> stringSet = stringRedisTemplate.keys(key);
            //删除缓存
            stringRedisTemplate.delete(stringSet);
            logger.info("清除 " + key + " 缓存");
        }
    }
}
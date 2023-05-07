package com.hives.common.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * @Author: zhangtao
 * @Date: 2023/4/6 14:39
 */
@Documented
@Constraint(
        validatedBy = {ListValueConstraintValidator.class}//用该校验器进行校验
)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListValue {
    String message() default "{com.atguigu.common.valid.ListValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int [] vals()default {};
}

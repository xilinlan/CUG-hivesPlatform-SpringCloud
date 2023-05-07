package com.hives.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Meng
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    private final Set<Integer> set=new HashSet<>();

    /**
     * 初始化方法
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {

        int [] vals= constraintAnnotation.vals();
        for(int val : vals){
            set.add(val);
        }
    }

    /**
     * 是否校验成功
     * @param value 需要校验的值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}

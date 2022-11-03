package com.destiny.origin.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @Description 校验 validate
 * @Date 2022-11-03 9:36 AM
 */
public final class ValidationUtils {

    /**
     * 校验数据
     * @param obj 入参
     * @param <T> 入参类型
     * @return
     */
    public static <T> String validate(T obj) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<T>> constraintViolations = factory.getValidator().validate(obj);

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            return constraintViolation.getPropertyPath() + ":" + constraintViolation.getMessage();
        }
        return null;
    }



}

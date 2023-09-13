package com.destiny.cat.anno;

import java.lang.annotation.*;

/**
 * @Description 字段加密注解
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonEncrypt {

    // 0-脱敏 1-加密
    int type() default 0;
    // 脱敏填充内容
    String value() default "*";
    // 开始位置
    int beginIdx() default 0;
    // 脱敏结束位置
    int endIdx() default 0;


}

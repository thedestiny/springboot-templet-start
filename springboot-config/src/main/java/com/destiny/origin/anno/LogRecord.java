package com.destiny.origin.anno;

import java.lang.annotation.*;

/**
 * @Description
 * @Author destiny
 * @Date 2022-03-30 2:22 PM
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface LogRecord {


}

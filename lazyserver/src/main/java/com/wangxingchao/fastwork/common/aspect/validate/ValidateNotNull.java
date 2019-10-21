package com.wangxingchao.fastwork.common.aspect.validate;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ValidateNotNull {
    String[] value() default {};
}

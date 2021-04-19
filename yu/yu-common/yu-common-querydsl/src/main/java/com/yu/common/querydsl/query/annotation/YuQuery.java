package com.yu.common.querydsl.query.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface YuQuery {

    Class<?> domain() default Void.class;

    YuJoin[] joins() default {};

    YuOrderColumn[] orders() default {};
}
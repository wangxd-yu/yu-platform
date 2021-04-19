package com.yu.common.querydsl.query.annotation;

import com.yu.common.querydsl.query.enums.YuOperatorEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface YuQueryColumn {

    String propName() default "";

    Class<?> domain() default Void.class;

    YuOperatorEnum operator() default YuOperatorEnum.EQUAL;
}
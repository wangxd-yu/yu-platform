package org.yu.common.querydsl.query.annotation;

import org.yu.common.querydsl.query.enums.YuOperatorEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangxd
 * @date 2020-10-10
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface YuQueryColumn {

    String propName() default "";

    Class<?> domain() default Void.class;

    YuOperatorEnum operator() default YuOperatorEnum.EQUAL;
}
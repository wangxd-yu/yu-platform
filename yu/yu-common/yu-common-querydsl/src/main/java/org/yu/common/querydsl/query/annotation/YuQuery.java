package org.yu.common.querydsl.query.annotation;

import java.lang.annotation.*;

/**
 * @author wangxd
 * @date 2020-10-10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface YuQuery {

    Class<?> domain() default Void.class;

    YuJoin[] joins() default {};

    YuOrderColumn[] orders() default {};
}
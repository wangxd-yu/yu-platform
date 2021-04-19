package com.yu.common.querydsl.query.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangxd
 * @date 2020-04-28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface YuOrderColumn {

    String column();

    Type type() default Type.DESC;

    Class<?> domain() default Void.class;

    /**
     * 关联类型
     */
    enum Type {
        //asc
        ASC,
        //desc
        DESC
    }
}
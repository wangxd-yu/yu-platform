package org.yu.common.querydsl.query.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangxd
 * @date 2020-10-10
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface YuJoinColumnGroup {

    YuJoinColumn[] columns();

    Type type() default Type.AND;

    /**
     * 关联条件
     */
    enum Type {
        //and
        AND,
        //or
        OR
    }
}


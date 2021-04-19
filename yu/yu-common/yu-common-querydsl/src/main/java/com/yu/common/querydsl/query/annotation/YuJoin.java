package com.yu.common.querydsl.query.annotation;

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
public @interface YuJoin {

    Class<?> domain() default Void.class;

    Type type() default Type.INNER_JOIN;

    YuJoinColumn[] columns() default {};

    YuJoinColumnGroup[] columnGroups() default {};

    /**
     * 关联类型
     */
    enum Type {
        //innerJoin
        INNER_JOIN("innerJoin"),
        //leftJoin
        LEFT_JOIN("leftJoin"),
        //rightJoin
        RIGHT_JOIN("rightJoin"),
        //fullJoin
        FULL_JOIN("fullJoin");

        private final String label;

        Type(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}


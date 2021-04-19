package com.yu.common.querydsl.query.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
        INNER_JOIN("innerJoin"),
        LEFT_JOIN("leftJoin"),
        RIGHT_JOIN("rightJoin"),
        FULL_JOIN("fullJoin");

        private String label;

        Type(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}


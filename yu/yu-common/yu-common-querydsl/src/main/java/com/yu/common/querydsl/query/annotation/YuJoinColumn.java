package com.yu.common.querydsl.query.annotation;


import com.yu.common.querydsl.query.enums.YuOperatorEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface YuJoinColumn {

    RelationType relationType() default RelationType.COLUMN;

    /**
     * 关联domain，默认为 SSCriteria 中的domain
     *
     * @return
     */
    Class<?> relationDomain() default Void.class;

    /**
     * 字段（DSLJoin中的 domain）
     *
     * @return
     */
    String column() default "";

    /**
     * 关联表字段
     *
     * @return
     */
    String relationColumn() default "";

    /**
     * 实体类对应的字段名称
     *
     * @return
     */
    String fieldName() default "";

    /**
     * 常量
     *
     * @return
     */
    String constant() default "";

    YuOperatorEnum operator() default YuOperatorEnum.EQUAL;

    /**
     * 关联类型
     */
    enum RelationType {
        COLUMN,         //表字段关联
        FIELD,
        CONST,

        RELATION_FIELD,
        RELATION_CONST
    }
}


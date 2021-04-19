package com.yu.common.querydsl.query.annotation;

import com.yu.common.querydsl.query.enums.YuOperatorEnum;

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
public @interface YuJoinColumn {

    RelationType relationType() default RelationType.COLUMN;

    /**
     * 关联domain，默认为 SSCriteria 中的domain
     */
    Class<?> relationDomain() default Void.class;

    /**
     * 字段（DSLJoin中的 domain）
     */
    String column() default "";

    /**
     * 关联表字段
     */
    String relationColumn() default "";

    /**
     * 实体类对应的字段名称
     */
    String fieldName() default "";

    /**
     * 常量
     */
    String constant() default "";

    YuOperatorEnum operator() default YuOperatorEnum.EQUAL;

    /**
     * 关联类型
     */
    enum RelationType {
        //两表字段关联
        COLUMN,
        //主表 field
        FIELD,
        //主表 常量
        CONST,
        //关联表 field
        RELATION_FIELD,
        //关联表 常量
        RELATION_CONST
    }
}


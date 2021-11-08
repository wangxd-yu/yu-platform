package org.yu.common.web.valid.annotation;

import org.yu.common.web.valid.constraint.YuValidConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 自定义参数校验注解
 *
 * @author wangxd
 * @date 2021-10-21 23:38
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YuValidConstraint.class)
@Repeatable(YuValid.List.class)
public @interface YuValid {
    /**
     * 失败后提示信息
     */
    String message() default "校验失败";

    /**
     * 分组
     *
     * @return
     */
    Class<?>[] group() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        YuValid[] value();
    }
}

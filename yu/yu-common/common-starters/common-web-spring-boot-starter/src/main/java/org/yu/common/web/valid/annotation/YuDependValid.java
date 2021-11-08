package org.yu.common.web.valid.annotation;

import org.yu.common.web.valid.constraint.YuDependConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * YU 自定义依赖校验
 *
 * @author wangxd
 * @date 2021-10-23 23:12
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YuDependConstraint.class)
@Repeatable(YuDependValid.List.class)
public @interface YuDependValid {
    /**
     * 失败后提示信息
     */
    String message() default "唯一校验失败";

    /**
     * 分组
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 实体类
     */
    Class<?> domain() default Void.class;

    /**
     * 依赖的字段名
     */
    String prop() default "";

    boolean exist() default true;

    @Target({ElementType.FIELD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        YuDependValid[] value();
    }
}

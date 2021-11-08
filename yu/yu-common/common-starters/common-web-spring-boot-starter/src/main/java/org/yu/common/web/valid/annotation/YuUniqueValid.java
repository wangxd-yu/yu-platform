package org.yu.common.web.valid.annotation;

import org.yu.common.web.valid.constraint.YuUniqueConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * YU 自定义唯一校验
 *
 * @author wangxd
 * @date 2021-10-23 23:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YuUniqueConstraint.class)
@Repeatable(YuUniqueValid.List.class)
public @interface YuUniqueValid {
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
     * 检索条件数组（默认 主键，以@Id注解的字段）
     */
    // String[] keys() default {};

    /**
     * 唯一字段数组
     */
    String[] props() default {};

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        YuUniqueValid[] value();
    }
}

package org.yu.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限 标记注解（标注在：controller 方法上），需要与 AbstractQuery 配合使用
 * @author wangxd
 * @date 2021-12-23 10:55
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface YuDataPermission {
}

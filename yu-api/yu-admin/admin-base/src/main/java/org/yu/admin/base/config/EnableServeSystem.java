package org.yu.admin.base.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-08-19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ServeSystemAutoConfigure.class)
public @interface EnableServeSystem {
}

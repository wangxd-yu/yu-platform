package org.yu.common.multidb.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author wangxd
 * @date 2021-06-21 14:26
 */
@Inherited
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DataSourceConfig.class})
public @interface EnableMultiDatabase {
}

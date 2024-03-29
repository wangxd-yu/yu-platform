package org.yu.admin.base.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-08-19
 */
@EnableAsync
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories("org.yu.serve.system.module")
@EntityScan("org.yu.serve.system.module")
@ComponentScan(value = {"org.yu.serve.system.module"})
public class ServeSystemAutoConfigure {
}

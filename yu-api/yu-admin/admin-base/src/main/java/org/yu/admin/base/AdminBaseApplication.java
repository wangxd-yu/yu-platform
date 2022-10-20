package org.yu.admin.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.yu.admin.base.config.EnableServeSystem;
import org.yu.common.multidb.config.EnableMultiDatabase;

/**
 * @author 王晓冬
 */
@EnableAsync
@EnableMultiDatabase
@EnableServeSystem
@EnableJpaRepositories("org.yu.admin")
@EntityScan("org.yu.admin")
@SpringBootApplication(scanBasePackages = {"org.yu.admin"}, exclude = {DataSourceAutoConfiguration.class})
public class AdminBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminBaseApplication.class, args);
    }

}

package org.yu.alonelaunch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.yu.alonelaunch.config.EnableServeSystem;
import org.yu.common.multidb.config.EnableMultiDatabase;

/**
 * @author 王晓冬
 */
@EnableAsync
@EnableMultiDatabase
@EnableServeSystem
@EnableJpaRepositories("org.yu.alonelaunch")
@EntityScan("org.yu.alonelaunch")
@SpringBootApplication(scanBasePackages = {"org.yu.alonelaunch"})
public class AloneLaunchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AloneLaunchApplication.class, args);
    }

}

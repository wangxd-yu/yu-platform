package org.yu.alonelaunch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.yu.alonelaunch.config.EnableServeSystem;
import org.yu.cloud.common.multidb.config.EnableMultiDatabase;

@EnableAsync
@EnableMultiDatabase
@EnableServeSystem
@SpringBootApplication(scanBasePackages = {"org.yu.alonelaunch"})
public class AloneLaunchApplication {

    public static void main(String[] args) {
        SpringApplication.run(AloneLaunchApplication.class, args);
    }

}

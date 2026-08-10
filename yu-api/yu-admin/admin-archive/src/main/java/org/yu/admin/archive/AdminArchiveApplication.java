package org.yu.admin.archive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
//@EnableMultiDatabase
//@EnableServeSystem
@EnableJpaRepositories(basePackages = {"org.yu.admin", "org.yu.serve"})
@EntityScan(basePackages = {"org.yu.admin", "org.yu.serve"})

//@ComponentScan(basePackages = {"org.yu.admin", "org.yu.serve"})
@SpringBootApplication(scanBasePackages = {"org.yu.admin", "org.yu.serve"}, exclude = {DataSourceAutoConfiguration.class})
public class AdminArchiveApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AdminArchiveApplication.class, args);
    }
}

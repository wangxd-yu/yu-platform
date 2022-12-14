package org.yu.serve.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author wangxd
 */
@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {"org.yu.serve.system"})
public class ServeSystemApp {
    public static void main(String[] args) {
        SpringApplication.run(ServeSystemApp.class, args);
    }
}

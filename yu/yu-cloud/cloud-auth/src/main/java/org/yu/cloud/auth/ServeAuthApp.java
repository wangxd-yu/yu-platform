package org.yu.cloud.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.yu.cloud.common.multidb.config.EnableMultiDatabase;

/**
 * @author wangxd
 * @date 2020-11-05 15:08
 */
@EnableMultiDatabase
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"org.yu.cloud.auth"})
public class ServeAuthApp {
    public static void main(String[] args) {
        SpringApplication.run(ServeAuthApp.class, args);
    }
}

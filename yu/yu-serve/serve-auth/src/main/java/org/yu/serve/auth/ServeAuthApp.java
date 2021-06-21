package org.yu.serve.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.yu.common.multidb.config.EnableMultiDatabase;

/**
 * @author wangxd
 * @date 2020-11-05 15:08
 */
@EnableMultiDatabase
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"org.yu.serve.auth"})
public class ServeAuthApp {
    public static void main(String[] args) {
        SpringApplication.run(ServeAuthApp.class, args);
    }
}

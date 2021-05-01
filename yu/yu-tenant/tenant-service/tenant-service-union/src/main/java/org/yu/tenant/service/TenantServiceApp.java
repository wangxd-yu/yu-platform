package org.yu.tenant.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author wangxd
 * @date 2021-04-09
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class TenantServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(TenantServiceApp.class, args);
    }
}

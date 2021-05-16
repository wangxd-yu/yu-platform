package org.yu.serve.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author wangxd
 */
@RemoteApplicationEventScan(basePackages = {"org.yu"})
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients("org.yu")
@SpringBootApplication(scanBasePackages = {"org.yu.serve.system", "org.yu.tenant.service"})
public class ServeSystemApp {
    public static void main(String[] args) {
        SpringApplication.run(ServeSystemApp.class, args);
    }
}

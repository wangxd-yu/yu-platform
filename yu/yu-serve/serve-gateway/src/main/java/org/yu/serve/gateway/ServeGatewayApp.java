package org.yu.serve.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wangxd
 * @date 2020/12/12
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServeGatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(ServeGatewayApp.class, args);
    }
}
package org.yu.cloud.common.multidb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yu.cloud.common.multidb.service.impl.DynamicDataSourceServiceFeignImpl;
import org.yu.common.multidb.service.DynamicDataSourceService;
import org.yu.tenant.service.api.datasource.feign.DataSourceFeign;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 动态数据源 Service Feign 模式配置
 *
 * @author wangxd
 * @date 2021-08-29
 */

@EnableFeignClients("org.yu.tenant.service.api.datasource")
@RemoteApplicationEventScan(basePackages = {"org.yu.tenant.service.api.datasource"})
@ConditionalOnClass(DataSourceFeign.class)
@ConditionalOnProperty(prefix = "yu.multidb", name = "type", havingValue = "feign")
@Configuration
public class DynamicDataSourceServiceFeignConfig {

    @Resource
    private DataSourceFeign dataSourceFeign;

    private DynamicDataSourceService dynamicDataSourceService;

    @PostConstruct
    public void init() {
        System.out.println("-------------------------------------- DynamicDataSourceServiceFeignConfig 类加载------------------");
    }

    @Bean
    public DynamicDataSourceService getDynamicDataSourceService() {
        if (dynamicDataSourceService == null) {
            synchronized (this) {
                if (dynamicDataSourceService == null) {
                    dynamicDataSourceService = new DynamicDataSourceServiceFeignImpl(dataSourceFeign);
                    return dynamicDataSourceService;
                }
            }
        }
        return dynamicDataSourceService;
    }
}

package org.yu.cloud.common.multidb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yu.cloud.common.multidb.properties.TenantDataSourceProperties;
import org.yu.cloud.common.multidb.service.DynamicDataSourceService;
import org.yu.cloud.common.multidb.service.impl.DynamicDataSourceServicePropImpl;

/**
 * 动态数据源 Service properties 模式配置
 *
 * @author wangxd
 * @date 2021-08-29
 */
@EnableConfigurationProperties(TenantDataSourceProperties.class)
@ConditionalOnProperty(prefix = "yu.multidb", name = "type", havingValue = "prop")
@Configuration
public class DynamicDataSourceServicePropConfig {

    private volatile DynamicDataSourceService dynamicDataSourceService;

    @Bean
    public DynamicDataSourceService getDynamicDataSourceService() {
        if (dynamicDataSourceService == null) {
            synchronized (this) {
                if (dynamicDataSourceService == null) {
                    dynamicDataSourceService = new DynamicDataSourceServicePropImpl();
                    return dynamicDataSourceService;
                }
            }
        }
        return dynamicDataSourceService;
    }
}

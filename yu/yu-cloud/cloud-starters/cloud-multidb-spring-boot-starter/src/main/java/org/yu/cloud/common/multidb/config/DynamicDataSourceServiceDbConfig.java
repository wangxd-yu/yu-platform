package org.yu.cloud.common.multidb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yu.cloud.common.multidb.properties.MultiTenantProperties;
import org.yu.cloud.common.multidb.service.DynamicDataSourceService;
import org.yu.cloud.common.multidb.service.impl.DynamicDataSourceServiceDbImpl;

/**
 * 动态数据源 Service DB 模式配置
 *
 * @author wangxd
 * @date 2021-08-29
 */
@ConditionalOnProperty(prefix = "yu.multidb", name = "type", havingValue = "db")
@Configuration
public class DynamicDataSourceServiceDbConfig {

    private volatile DynamicDataSourceService dynamicDataSourceService;

    @Bean
    public DynamicDataSourceService getDynamicDataSourceService() {
        if (dynamicDataSourceService == null) {
            synchronized (this) {
                if (dynamicDataSourceService == null) {
                    dynamicDataSourceService = new DynamicDataSourceServiceDbImpl();
                    return dynamicDataSourceService;
                }
            }
        }
        return dynamicDataSourceService;
    }

    @Bean
    public MultiTenantProperties getMultiTenantProperties() {
        //TODO 后续处理（impl实现类中赋值）
        return new MultiTenantProperties();
    }
}

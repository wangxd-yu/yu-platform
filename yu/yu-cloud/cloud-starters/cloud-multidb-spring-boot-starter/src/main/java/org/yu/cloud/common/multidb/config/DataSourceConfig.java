package org.yu.cloud.common.multidb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.yu.cloud.common.multidb.service.DynamicDataSourceService;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 数据源DataSource配置
 *
 * @author wangxd
 * @date 2021-03-18 22:19
 */
@ConditionalOnProperty(prefix = "yu.multidb", name = "enabled", havingValue = "true")
@Configuration
@Import({DynamicDataSourceServiceDbConfig.class, DynamicDataSourceServicePropConfig.class, DynamicDataSourceServiceFeignConfig.class})
public class DataSourceConfig {

    @Resource
    private DynamicDataSourceService dynamicDataSourceService;

    @Bean
    public DataSource getDefaultDataSource() {
        return dynamicDataSourceService.getSpringDefaultDataSource();
    }

    @Bean
    @Primary
    public DynamicDataSource springDataSource() {
        DynamicDataSource routing = new DynamicDataSource();
        routing.setTargetDataSources(dynamicDataSourceService.getTargetDataSource());
        routing.setDefaultTargetDataSource(getDefaultDataSource());
        return routing;
    }
}

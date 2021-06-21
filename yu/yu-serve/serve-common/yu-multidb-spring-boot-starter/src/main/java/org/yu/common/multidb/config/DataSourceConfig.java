package org.yu.common.multidb.config;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.yu.common.multidb.service.DynamicDataSourceService;
import org.yu.common.multidb.service.impl.DynamicDataSourceServiceImpl;
import org.yu.tenant.service.api.datasource.feign.DataSourceFeign;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 数据源DataSource配置
 *
 * @author wangxd
 * @date 2021-03-18 22:19
 */
@EnableFeignClients("org.yu.tenant.service.api.datasource")
@RemoteApplicationEventScan(basePackages = {"org.yu.tenant.service.api.datasource"})
@Configuration
public class DataSourceConfig {

    @Resource
    private DataSourceFeign dataSourceFeign;

    private DynamicDataSourceService dynamicDataSourceService;

    @Bean
    public DynamicDataSourceService getDynamicDataSourceService() {
        if(dynamicDataSourceService == null) {
            synchronized (this) {
                if(dynamicDataSourceService == null) {
                    dynamicDataSourceService = new DynamicDataSourceServiceImpl(dataSourceFeign);
                    return dynamicDataSourceService;
                }
            }
        }
        return dynamicDataSourceService;
    }

    @Bean
    public DataSource getDefaultDataSource() {
        return getDynamicDataSourceService().getSpringDefaultDataSource();
    }

    @Bean
    @Primary
    public DynamicDataSource springDataSource() {
        DynamicDataSource routing = new DynamicDataSource();
        routing.setTargetDataSources(getDynamicDataSourceService().getTargetDataSource());
        routing.setDefaultTargetDataSource(getDefaultDataSource());
        return routing;
    }
}

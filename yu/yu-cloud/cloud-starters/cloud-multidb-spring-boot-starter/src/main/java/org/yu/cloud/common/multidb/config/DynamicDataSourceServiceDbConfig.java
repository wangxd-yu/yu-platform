package org.yu.cloud.common.multidb.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yu.cloud.common.multidb.repository.DataSourceDBRepository;
import org.yu.cloud.common.multidb.service.DynamicDataSourceService;
import org.yu.cloud.common.multidb.service.impl.DynamicDataSourceServiceDBImpl;
import org.yu.tenant.service.api.datasource.feign.DataSourceFeign;

import javax.annotation.Resource;
import javax.sql.DataSource;

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
        if(dynamicDataSourceService == null) {
            synchronized (this) {
                if(dynamicDataSourceService == null) {
                    dynamicDataSourceService = new DynamicDataSourceServiceDBImpl();
                    return dynamicDataSourceService;
                }
            }
        }
        return dynamicDataSourceService;
    }
}

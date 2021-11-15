package org.yu.common.multidb.service.impl;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.yu.common.multidb.config.DynamicDataSource;
import org.yu.common.multidb.properties.MultiTenantProperties;
import org.yu.common.multidb.service.DynamicDataSourceService;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源处理实现类-prop
 *
 * @author wangxd
 * @date 2021-03-18 22:22
 */
@Slf4j
public class DynamicDataSourceServicePropImpl implements DynamicDataSourceService {

    private DataSource defaultDataSource;

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Resource
    private MultiTenantProperties multiTenantProperties;

    @Override
    public DataSource getSpringDefaultDataSource() {
        if (defaultDataSource == null) {
            defaultDataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        }
        return defaultDataSource;
    }

    @Override
    public Map<Object, Object> getTargetDataSource() {
        multiTenantProperties.getTenants().forEach((key, value) ->
                DynamicDataSource.dynamicTargetDataSources.put(
                        key,
                        value.getDatasource().initializeDataSourceBuilder().type(HikariDataSource.class).build()
                )
        );
        return DynamicDataSource.dynamicTargetDataSources;
    }
}

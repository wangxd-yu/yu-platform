package org.yu.common.multidb.service.impl;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.yu.common.multidb.config.DynamicDataSource;
import org.yu.common.multidb.service.DynamicDataSourceService;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源处理实现类-db
 *
 * @author wangxd
 * @date 2021-03-18 22:22
 */
@Slf4j
public class DynamicDataSourceServiceDbImpl implements DynamicDataSourceService {

    private DataSource defaultDataSource;

    @Resource
    private DataSourceProperties dataSourceProperties;

    @Override
    public DataSource getSpringDefaultDataSource() {
        if (defaultDataSource == null) {
            defaultDataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        }
        return defaultDataSource;
    }

    @Override
    public Map<Object, Object> getTargetDataSource() {
        String selectDatasourceSql = "select tenant_id, url, username, password from tb_datasource";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getSpringDefaultDataSource());
        jdbcTemplate.query(selectDatasourceSql, (resultSet, i) -> {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl(resultSet.getString("url"));
            dataSource.setUsername(resultSet.getString("username"));
            dataSource.setPassword(resultSet.getString("password"));
            DynamicDataSource.dynamicTargetDataSources.put(resultSet.getString("tenant_id"), dataSource);
            return null;
        });
        return DynamicDataSource.dynamicTargetDataSources;
    }
}

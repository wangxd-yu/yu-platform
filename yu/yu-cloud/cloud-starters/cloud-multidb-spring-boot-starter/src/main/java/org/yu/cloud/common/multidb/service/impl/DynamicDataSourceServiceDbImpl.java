package org.yu.cloud.common.multidb.service.impl;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.yu.cloud.common.multidb.config.DynamicDataSource;
import org.yu.cloud.common.multidb.service.DynamicDataSourceService;

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
        /*HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/yu_tenant_platform?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        return dataSource2;*/
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
        /*DataSourceDBDO dataSourceDBDO1 = new DataSourceDBDO();
        dataSourceDBDO1.setTenantId(1000L);
        dataSourceDBDO1.setUrl("jdbc:mysql://127.0.0.1:3306/yu_tenant_1?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false");
        dataSourceDBDO1.setUsername("root");
        dataSourceDBDO1.setPassword("123456");

        DataSourceDBDO dataSourceDBDO2 = new DataSourceDBDO();
        dataSourceDBDO2.setTenantId(1001L);
        dataSourceDBDO2.setUrl("jdbc:mysql://127.0.0.1:3306/yu_tenant_2?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=UTF-8&allowPublicKeyRetrieval=true&useSSL=false");
        dataSourceDBDO2.setUsername("root");
        dataSourceDBDO2.setPassword("123456");

        List<DataSourceDBDO> dataSourceDBDOS = Arrays.asList(
                dataSourceDBDO1, dataSourceDBDO2
        );
        dataSourceDBDOS.forEach(dataSourceDBDO -> {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl(dataSourceDBDO.getUrl());
            dataSource.setUsername(dataSourceDBDO.getUsername());
            dataSource.setPassword(dataSourceDBDO.getPassword());
            DynamicDataSource.dynamicTargetDataSources.put(dataSourceDBDO.getTenantId().toString(), dataSource);
        });*/
        return DynamicDataSource.dynamicTargetDataSources;
    }
}

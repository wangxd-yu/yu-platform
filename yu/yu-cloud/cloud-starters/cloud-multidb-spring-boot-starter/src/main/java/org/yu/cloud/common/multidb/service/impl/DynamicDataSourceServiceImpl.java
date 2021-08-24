package org.yu.cloud.common.multidb.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.yu.cloud.common.multidb.config.DynamicDataSource;
import org.yu.cloud.common.multidb.service.DynamicDataSourceService;
import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.api.MultiDataTypeEnum;
import org.yu.tenant.service.api.datasource.domain.DataSourceDO;
import org.yu.tenant.service.api.datasource.feign.DataSourceFeign;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源处理实现类
 *
 * @author wangxd
 * @date 2021-03-18 22:22
 */
@Slf4j
public class DynamicDataSourceServiceImpl implements DynamicDataSourceService {

    private final DataSourceFeign dataSourceFeign;

    public DynamicDataSourceServiceImpl(DataSourceFeign dataSourceFeign) {
        this.dataSourceFeign = dataSourceFeign;
    }

    @Override
    public DataSource getSpringDefaultDataSource() {
        HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setJdbcUrl("jdbc:mysql://192.168.103.184:3306/yu_tenant_1");
        dataSource2.setUsername("root");
        dataSource2.setPassword("Sdses@2020");
        return dataSource2;
    }

    @Override
    public Map<Object, Object> getTargetDataSource() {
        ResponseEntity<Object> responseEntity = dataSourceFeign.listDataSources(null, MultiDataTypeEnum.LIST);
        log.info("[dataSourceFeign]:({})", responseEntity.getBody());
        ObjectMapper mapper = new ObjectMapper();
        MultiDataResult<DataSourceDO> dataSourceDOMultiDataResult = mapper.convertValue(
                responseEntity.getBody(),
                new TypeReference<MultiDataResult<DataSourceDO>>() {
                }
        );
        dataSourceDOMultiDataResult.getData().forEach(dataSourceDO -> {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl(dataSourceDO.getUrl());
            dataSource.setUsername(dataSourceDO.getUsername());
            dataSource.setPassword(dataSourceDO.getPassword());
            DynamicDataSource.dynamicTargetDataSources.put(dataSourceDO.getTenantId().toString(), dataSource);
        });
        /*HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/yu_cloud");
        dataSource2.setUsername("root");
        dataSource2.setPassword("123456");
        DynamicDataSource.dynamicTargetDataSources.put("1000", dataSource2);

        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/yu_tenant_1");
        dataSource1.setUsername("root");
        dataSource1.setPassword("Yu@2020");
        DynamicDataSource.dynamicTargetDataSources.put("1001", dataSource1);*/
        return DynamicDataSource.dynamicTargetDataSources;
    }
}

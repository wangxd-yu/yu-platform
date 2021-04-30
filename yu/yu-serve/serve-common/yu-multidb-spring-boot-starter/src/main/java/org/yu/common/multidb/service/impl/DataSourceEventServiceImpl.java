package org.yu.common.multidb.service.impl;

import org.springframework.stereotype.Service;
import org.yu.common.multidb.config.DynamicDataSource;
import org.yu.tenant.service.api.datasource.service.DataSourceEventService;

/**
 * 数据源消息事件 实现
 *
 * @author wangxd
 * @date 2021-04-09
 */
@Service
public class DataSourceEventServiceImpl implements DataSourceEventService {
    @Override
    public void enableDataSource(Long dataSourceId) {
        //TODO
        DynamicDataSource.createDataSource(null, null, null, null, null, null);
    }

    @Override
    public void disableDataSource(Long dataSourceId) {
        //TODO
        String tenantId = "dataSourceId 转换";
        DynamicDataSource.deleteDatasources(tenantId);
    }
}

package org.yu.tenant.service.api.datasource.service;

/**
 * 数据源事件 service
 *
 * @author wangxd
 * @date 2021-04-09
 */
public interface DataSourceEventService {
    /**
     * 启用
     *
     * @param dataSourceId 主键
     */
    void enableDataSource(Long dataSourceId);

    /**
     * 停用
     *
     * @param dataSourceId 主键
     */
    void disableDataSource(Long dataSourceId);
}

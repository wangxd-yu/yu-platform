package org.yu.tenant.service.datasource.service;

import org.yu.common.querydsl.service.DslBaseService;
import org.yu.tenant.service.api.datasource.domain.DataSourceDO;

/**
 * 数据源-接口
 *
 * @author wangxd
 * @date 2021-04-02 14:51
 */
public interface DataSourceService extends DslBaseService<DataSourceDO, Long> {
    /**
     * 启用
     *
     * @param datasourceId 主键
     */
    void enableDataSource(Long datasourceId);

    /**
     * 停用
     *
     * @param datasourceId 主键
     */
    void disableDataSource(Long datasourceId);
}

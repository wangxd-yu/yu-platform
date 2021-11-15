package org.yu.common.multidb.service;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态数据源处理接口
 *
 * @author wangxd
 * @date 2021-03-18 22:21
 */
public interface DynamicDataSourceService {
    /**
     * 获取默认DataSource
     *
     * @return 默认
     */
    DataSource getSpringDefaultDataSource();

    /**
     * 获取多数据源DataSource
     *
     * @return 多数据源
     */
    Map<Object, Object> getTargetDataSource();
}

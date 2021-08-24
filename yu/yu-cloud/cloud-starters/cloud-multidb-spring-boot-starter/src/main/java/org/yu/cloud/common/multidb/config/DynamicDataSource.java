package org.yu.cloud.common.multidb.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.yu.common.core.context.YuContextHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring 动态数据源
 *
 * @author wangxd
 * @date 2021-03-08 22:35
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static Map<Object, Object> dynamicTargetDataSources = new HashMap<>(8);

    @Override
    protected Object determineCurrentLookupKey() {
        return YuContextHolder.getYuContext() != null ? YuContextHolder.getYuContext().getTenantId().toString() : null;
    }

    /**
     * 创建数据源
     *
     * @param key
     * @param driveClass
     * @param url
     * @param username
     * @param password
     * @param databaseType
     * @return
     */
    public static boolean createDataSource(String key, String driveClass, String url, String username, String password, String databaseType) {
        //TODO
        return false;
    }

    /**
     * 删除数据源
     *
     * @param tenantId 租户id
     * @return
     */
    public static boolean deleteDatasources(String tenantId) {
        //TODO
        return false;
    }
}

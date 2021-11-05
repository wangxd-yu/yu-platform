package org.yu.cloud.common.multidb.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.util.List;

/**
 * @author wangxd
 * @date 2021-11-03 14:16
 */
@Data
public class TenantProperties {

    /**
     * 超级管理员用户
     */
    private List<String> admins;

    /**
     * 数据源
     */
    private DataSourceProperties datasource;
}

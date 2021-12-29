package org.yu.common.multidb.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-09-24
 */
@Data
@ConfigurationProperties(prefix = "yu.multidb")
public class MultiTenantProperties {
    private Map<String, TenantProperties> tenants;
}

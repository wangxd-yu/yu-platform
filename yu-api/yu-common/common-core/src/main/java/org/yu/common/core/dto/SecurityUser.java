package org.yu.common.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author wangxd
 * @date 2020-11-27 9:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SecurityUser {

    private String id;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 租户ID
     */
    private String tenantId;
    private String clientId;
    private String username;
    private Set<String> roles;
}

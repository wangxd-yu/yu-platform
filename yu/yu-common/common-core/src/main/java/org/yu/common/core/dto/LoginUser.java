package org.yu.common.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-27 9:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginUser {

    private String id;
    /**
     * 部门NO
     */
    private String deptNo;
    /**
     * 租户ID
     */
    private Integer tenantId;
    private String clientId;
    private String username;
    private List<String> roles;
}

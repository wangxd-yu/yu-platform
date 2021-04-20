package org.yu.common.core.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by sdses on 2020/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginUser {

    private Long id;
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

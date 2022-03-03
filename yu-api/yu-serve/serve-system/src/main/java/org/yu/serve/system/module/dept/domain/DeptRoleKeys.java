package org.yu.serve.system.module.dept.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wangxd
 * @date 2022-03-02
 */
@Getter
@Setter
@EqualsAndHashCode
public class DeptRoleKeys implements Serializable {
    private String deptId;
    private String roleId;
}

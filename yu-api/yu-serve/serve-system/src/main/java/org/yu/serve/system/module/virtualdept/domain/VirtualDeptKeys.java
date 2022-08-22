package org.yu.serve.system.module.virtualdept.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wangxd
 */
@Getter
@Setter
@EqualsAndHashCode
public class VirtualDeptKeys implements Serializable {

    private String virtualDeptId;

    private String deptId;
}

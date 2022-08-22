package org.yu.serve.system.module.virtualdept.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author wangxd
 * @date 2022-04-21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(VirtualDeptKeys.class)
@Table(name = "sys_virtual_dept_dept")
public class VirtualDeptDeptDO {
    @Id
    @Column(name = "virtual_dept_id")
    private String virtualDeptId;
    @Id
    @Column(name = "dept_id")
    private String deptId;
}

package org.yu.serve.system.module.dept.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOField;
import org.yu.serve.system.module.dept.domain.DeptRoleDO;
import org.yu.serve.system.module.role.domain.RoleDO;

import java.time.LocalDateTime;

/**
 * @author wangxd
 * @date 2022-03-02
 */
@Data
@YuDTO(domain = DeptRoleDO.class)
public class DeptRoleDTO {

    private String deptId;


    private String roleId;

    @YuDTOField(domain = RoleDO.class, propName = "name")
    private String roleName;

    @YuDTOField(domain = RoleDO.class, propName = "remark")
    private String roleRemark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime bindTime;
}

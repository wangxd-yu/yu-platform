package org.yu.serve.system.module.user.dto;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOField;
import org.yu.common.querydsl.query.annotation.YuDTOTransient;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.user.domain.UserDO;

import java.util.Set;

/**
 * @author wangxd
 * @date 2020-12-01 15:26
 */
@Data
@YuDTO(domain = UserDO.class)
public class UserFullDTO {

    private String username;

    private String name;

    private String deptNo;

    @YuDTOField(domain = DeptDO.class, propName = "code")
    private String deptCode;

    @YuDTOField(domain = DeptDO.class, propName = "name")
    private String deptName;

    @YuDTOTransient
    private Set<String> roles;
}

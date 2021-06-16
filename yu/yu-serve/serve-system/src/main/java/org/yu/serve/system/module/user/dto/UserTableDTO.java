package org.yu.serve.system.module.user.dto;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOField;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.user.domain.UserDO;

/**
 * 查询表格展示 形式
 *
 * @author wangxd
 * @date 2020-12-01 19:27
 */
@Data
@YuDTO(domain = UserDO.class)
public class UserTableDTO {

    private Long id;

    private String username;

    private String name;

    private Boolean enabled;

    @YuDTOField(domain = DeptDO.class, propName = "name")
    private String deptName;
}

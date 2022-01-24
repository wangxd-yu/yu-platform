package org.yu.serve.system.module.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOField;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.user.domain.UserDO;

import java.time.LocalDateTime;

/**
 * 查询表格展示 形式
 *
 * @author wangxd
 * @date 2020-12-01 19:27
 */
@Data
@YuDTO(domain = UserDO.class)
public class UserTableDTO {

    private String id;

    private String username;

    private String name;

    private Boolean enabled;

    private String portraitUrl;

    @YuDTOField(domain = DeptDO.class, propName = "name")
    private String deptName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}

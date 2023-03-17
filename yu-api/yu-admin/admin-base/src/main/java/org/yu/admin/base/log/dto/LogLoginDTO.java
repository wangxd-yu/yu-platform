package org.yu.admin.base.log.dto;

import lombok.Data;
import org.yu.admin.base.log.domain.LogLoginDO;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOField;
import org.yu.serve.system.module.dept.domain.DeptDO;

import java.time.LocalDateTime;

/**
 * @author wangxd
 * @date 2023-03-16 23:30
 */
@Data
@YuDTO(domain = LogLoginDO.class)
public class LogLoginDTO {

    private String username;

    private String ip;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    private LocalDateTime createTime;

    @YuDTOField(domain = DeptDO.class, propName = "name")
    private String deptName;
}

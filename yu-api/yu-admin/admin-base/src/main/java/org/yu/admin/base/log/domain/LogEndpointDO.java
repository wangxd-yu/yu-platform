package org.yu.admin.base.log.domain;

import lombok.*;
import org.yu.common.querydsl.domain.DslBaseTenantDO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2021-12-01 21:47
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "log_endpoint")
public class LogEndpointDO extends DslBaseTenantDO<String> {

    private String endpointId;
    /**
     * 登录用户名
     */
    private String username;

    private String userId;

    private String ip;

    private String method;

    private String handler;

    private String pattern;

    private String url;

    private String request;

    private String response;

    private Integer httpStatus;

    /**
     * 耗时
     */
    private Integer time;
}

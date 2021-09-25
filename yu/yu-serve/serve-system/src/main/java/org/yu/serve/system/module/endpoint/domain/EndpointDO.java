package org.yu.serve.system.module.endpoint.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yu.common.querydsl.domain.DslBaseTenantDO;

import javax.persistence.*;

/**
 * 端点
 *
 * @author wangxd
 * @date 2021-09-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_endpoint")
public class EndpointDO extends DslBaseTenantDO<String> {

    private String label;

    private String pattern;

    @Enumerated(EnumType.STRING)
    private RequestMethod method;

    @Column(name = "is_enabled")
    private Boolean enabled;
}

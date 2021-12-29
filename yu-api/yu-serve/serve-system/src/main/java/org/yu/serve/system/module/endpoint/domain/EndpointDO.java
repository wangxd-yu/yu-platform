package org.yu.serve.system.module.endpoint.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yu.common.querydsl.domain.DslBaseTenantDO;
import org.yu.common.web.valid.annotation.YuUniqueValid;

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
@YuUniqueValid(props = {"pattern", "method"}, message = "已存在端点：【${method}】${pattern}")
public class EndpointDO extends DslBaseTenantDO<String> {

    private String label;

    private String pattern;

    @Enumerated(EnumType.STRING)
    private RequestMethod method;

    @Column(name = "is_access_enabled")
    private Boolean accessEnabled;

    @Column(name = "is_log_enabled")
    private Boolean logEnabled;
}

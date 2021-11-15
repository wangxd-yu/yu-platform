package org.yu.common.multidb.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 数据源DO
 *
 * @author wangxd
 * @date 2021-03-25 17:18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_datasource")
public class DataSourceDO extends DslBaseDO<Long> {

    /**
     * 组id
     */
    private Long groupId;

    /**
     * 租户id
     */
    private Long tenantId;

    /**
     * 连接信息
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 数据库类型
     */
    private String type;

    @Column(name = "is_enabled")
    private Boolean enabled;
}

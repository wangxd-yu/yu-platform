package org.yu.alonelaunch.log.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseDO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author wangxd
 * @date 2021-11-09 22:03
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "log_login")
public class LogLoginDO extends DslBaseDO<String> {
    /**
     * 登录用户名
     */
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
}

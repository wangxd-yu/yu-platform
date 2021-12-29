package org.yu.serve.system.module.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseDeptDO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * @author wangxd
 * @date 2020-11-09 09:12
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "sys_user")
public class UserDO extends DslBaseDeptDO<String> implements Serializable {
    @NotBlank
    private String username;

    private String name;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean enabled;

    @JsonIgnore
    private String password;

    @Transient
    private Set<String> roleIds;
}

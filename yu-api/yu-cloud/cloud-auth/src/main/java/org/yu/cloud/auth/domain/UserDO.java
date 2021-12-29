package org.yu.cloud.auth.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * @author yu
 * @date 2020-11-06 13:37
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "sys_user")
public class UserDO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String deptNo;

    @NotBlank
    private String username;

    private String name;

    @NotNull
    @Column(name = "is_enabled")
    private Boolean enabled;

    private String password;

    private Integer tenantId;

    @ManyToMany
    @JoinTable(name = "sys_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<RoleDO> roles;
}
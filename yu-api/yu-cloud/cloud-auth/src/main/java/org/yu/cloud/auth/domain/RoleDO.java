package org.yu.cloud.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author wangxd
 * @date 2020-11-06 13:37
 */
@Data
@EqualsAndHashCode
@Entity
@Table(name = "sys_role")
public class RoleDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;
}

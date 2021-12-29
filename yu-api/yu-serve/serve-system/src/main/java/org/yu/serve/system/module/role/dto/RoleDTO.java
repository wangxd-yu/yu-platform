package org.yu.serve.system.module.role.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author wangxd
 * @date 2020-11-26 10:31
 */
@Data
public class RoleDTO {
    @NotBlank
    private String code;

    @NotBlank
    private String name;
}

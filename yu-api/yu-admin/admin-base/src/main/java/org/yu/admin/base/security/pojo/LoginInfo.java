package org.yu.admin.base.security.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author wangxd
 * @date 2021-12-10 16:47
 */
@Getter
@Setter
public class LoginInfo {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String tenantId;

    private String code;

    private String uuid = "";

    @Override
    public String toString() {
        return "{username=" + username + ", password= ******}";
    }
}

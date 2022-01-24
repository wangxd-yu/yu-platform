package org.yu.serve.system.module.user.dto;

import lombok.Data;

/**
 * @author wangxd
 * @date 2022-01-15 23:41
 */
@Data
public class UserBaseInfo {

    private String username;

    private String email;

    private String name;

    private String portraitBase64;

}

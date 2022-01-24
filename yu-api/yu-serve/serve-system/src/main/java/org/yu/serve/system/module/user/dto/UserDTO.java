package org.yu.serve.system.module.user.dto;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOTransient;
import org.yu.serve.system.module.user.domain.UserDO;

import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-26 10:28
 */
@Data
@YuDTO(domain = UserDO.class)
public class UserDTO {

    private String username;

    private String name;

    private String portraitUrl;

    @YuDTOTransient
    private List<String> roles;
}

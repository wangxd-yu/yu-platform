package org.yu.serve.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by yu on 2020/6/19.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;
}

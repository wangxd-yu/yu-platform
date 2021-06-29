package org.yu.serve.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.common.context.holder.LoginHolder;
import org.yu.common.core.dto.LoginUser;

/**
 * 登录用户接口
 *
 * @author wangxd
 * @date 2021-06-29
 */
@RestController
@RequestMapping
public class UserController {

    private final LoginHolder loginHolder;

    public UserController(LoginHolder loginHolder) {
        this.loginHolder = loginHolder;
    }

    @GetMapping("/currentUser")
    public LoginUser currentUser() {
        return loginHolder.loginUser();
    }
}

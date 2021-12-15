package org.yu.alonelaunch.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.alonelaunch.security.pojo.LoginInfo;
import org.yu.alonelaunch.security.pojo.SecurityUser;
import org.yu.alonelaunch.security.util.JwtTokenUtil;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.serve.system.util.PasswordUtil;

import javax.annotation.Resource;

/**
 * @author wangxd
 * @date 2021-12-10 14:49
 */
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Resource
    private UserDetailsService userDetailsService;

    @PostMapping("login")
    public ResponseEntity<Object> login(@Validated @RequestBody LoginInfo loginUser) {
        if (loginUser.getTenantId() != null) {
            if (YuContextHolder.getYuContext() == null) {
                new YuContextHolder(new YuContext());
            }
            YuContextHolder.getYuContext().setTenantId(loginUser.getTenantId());
        }

        final SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(loginUser.getUsername());
        if (!PasswordUtil.checkPassword(loginUser.getPassword(), securityUser.getPassword())) {
            throw new AccountExpiredException("用户名或密码错误");
        }

        if (!securityUser.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }
/*
        if (!DeptUtil.findById(securityUser.getDeptId()).getEnabled()) {
            throw new AccountExpiredException("所属部门已停用，请联系管理员");
        }*/

        // 返回 认证信息
        return ResponseEntity.ok(JwtTokenUtil.generateAuthenticationInfo(securityUser));
    }
}

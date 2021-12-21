package org.yu.alonelaunch.security.controller;

import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yu.alonelaunch.log.domain.LogLoginDO;
import org.yu.alonelaunch.log.service.LogLoginService;
import org.yu.alonelaunch.security.pojo.LoginInfo;
import org.yu.alonelaunch.security.pojo.SecurityUser;
import org.yu.alonelaunch.security.util.JwtTokenUtil;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.web.util.AddressUtil;
import org.yu.serve.system.util.PasswordUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wangxd
 * @date 2021-12-10 14:49
 */
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private LogLoginService logLoginService;

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
        logLoginService.asyncSave(this.map2LogLoginDO(loginUser.getUsername()));
        // 返回 认证信息
        return ResponseEntity.ok(JwtTokenUtil.generateAuthenticationInfo(securityUser));
    }

    private LogLoginDO map2LogLoginDO(String username) {
        LogLoginDO logLoginDO = new LogLoginDO();
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
        logLoginDO.setUsername(username);
        logLoginDO.setBrowser(ua.getBrowser().getName());
        String ip = ServletUtil.getClientIP(request);
        logLoginDO.setIp(ip);
        logLoginDO.setLocation(AddressUtil.getRealAddressByIP(ip));
        logLoginDO.setOs(ua.getOs().getName());
        return logLoginDO;
    }
}

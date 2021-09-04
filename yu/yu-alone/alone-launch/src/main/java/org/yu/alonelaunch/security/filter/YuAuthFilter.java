package org.yu.alonelaunch.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yu.alonelaunch.security.pojo.SecurityUser;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.dto.LoginUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-09-04
 */
@Component
public class YuAuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        populateYuContextHolder();
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        YuContextHolder.clearContext();
    }

    private void populateYuContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal != null && principal instanceof SecurityUser) {
            SecurityUser securityUser = (SecurityUser) principal;
            LoginUser loginUser = new LoginUser();
            loginUser.setId(securityUser.getId());
            loginUser.setClientId(securityUser.getClientId());
            loginUser.setTenantId(securityUser.getTenantId());
            loginUser.setUsername(securityUser.getUsername());
            loginUser.setDeptNo(securityUser.getDeptNo());
            loginUser.setRoles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            YuContext yuContext = new YuContext();
            yuContext.setClientUser(loginUser);
            new YuContextHolder(yuContext);
        }
    }
}

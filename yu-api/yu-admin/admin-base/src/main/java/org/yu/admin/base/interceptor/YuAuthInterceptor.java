package org.yu.admin.base.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.yu.admin.base.util.TenantUtil;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.dto.SecurityUser;

import javax.servlet.http.HttpServletRequest;

/**
 * YU 请求拦截器，用于处理 YuContextHolder填充、端点权限控制
 *
 * @author wangxd
 * @date 2021-09-05
 */
public interface YuAuthInterceptor extends HandlerInterceptor {

    void init();

    default String getPath(HttpServletRequest httpServletRequest) {
        String method = httpServletRequest.getMethod();
        String bestMatchingPattern = (String) httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return bestMatchingPattern + "[" + method + "]";
    }

    default YuContext populateYuContext() {
        YuContext yuContext = new YuContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof org.yu.admin.base.security.pojo.SecurityUser) {
            org.yu.admin.base.security.pojo.SecurityUser securityUser = (org.yu.admin.base.security.pojo.SecurityUser) principal;
            SecurityUser loginUser = new SecurityUser();
            loginUser.setId(securityUser.getId());
            loginUser.setClientId(securityUser.getClientId());
            loginUser.setTenantId(securityUser.getTenantId());
            loginUser.setUsername(securityUser.getUsername());
            loginUser.setDeptId(securityUser.getDeptId());
            // loginUser.setRoles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
            loginUser.setRoles(securityUser.getRoles());

            yuContext.setSecurityUser(loginUser);
        } else {
            yuContext.setTenantId(TenantUtil.getTenantId());
        }

        return yuContext;
    }
}

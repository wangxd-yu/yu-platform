package org.yu.alonelaunch.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.yu.alonelaunch.constant.MessageConstant;
import org.yu.alonelaunch.security.pojo.SecurityUser;
import org.yu.alonelaunch.util.TenantUtil;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.dto.LoginUser;
import org.yu.common.core.event.EndpointAccessEvent;
import org.yu.common.multidb.properties.MultiTenantProperties;
import org.yu.serve.system.module.endpoint.service.EndpointService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * YU 请求拦截器，用于处理 YuContextHolder填充、端点权限控制
 *
 * @author wangxd
 * @date 2021-09-05
 */
@Slf4j
public class YuAuthInterceptor implements HandlerInterceptor {

    @Resource
    private EndpointService endpointService;

    @Resource
    private MultiTenantProperties multiTenantProperties;

    /**
     * 权限路径 map
     * 存放 不同tenant的 地址权限 对应关系
     * 不同租户的 端点 访问权限控制 数据结构
     */
    private static Map<String, Map<String, Set<String>>> tenantAccessPathMap;

    public void init() {
        tenantAccessPathMap = new HashMap<>(multiTenantProperties.getTenants().size());
        multiTenantProperties.getTenants().forEach((tenantId, value) -> {
            YuContext yuContext = new YuContext();
            new YuContextHolder(yuContext);
            YuContextHolder.getYuContext().setTenantId(tenantId);
            tenantAccessPathMap.put(tenantId, endpointService.getAccessEndpoints());
            YuContextHolder.clearContext();
        });
    }

    @EventListener(classes = EndpointAccessEvent.class)
    public void accessListener() {
        // 此处如果改为异步执行， tenantId 需要传入，不能直接从 YuContextHolder中获取(使用阿里 TtlExecutors 解决)
        tenantAccessPathMap.put(YuContextHolder.getTenantId(), endpointService.getAccessEndpoints());
    }

    private YuContext populateYuContext() {
        YuContext yuContext = new YuContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser) {
            SecurityUser securityUser = (SecurityUser) principal;
            LoginUser loginUser = new LoginUser();
            loginUser.setId(securityUser.getId());
            loginUser.setClientId(securityUser.getClientId());
            loginUser.setTenantId(securityUser.getTenantId());
            loginUser.setUsername(securityUser.getUsername());
            loginUser.setDeptNo(securityUser.getDeptNo());
            loginUser.setRoles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));

            yuContext.setClientUser(loginUser);
        } else {
            yuContext.setTenantId(TenantUtil.getTenantId());
        }

        return yuContext;
    }

    private void checkPathPermission(String path) {
        Map<String, Set<String>> pathRolesMap = tenantAccessPathMap.get(YuContextHolder.getTenantId());
        Set<String> roles = pathRolesMap == null ? null : pathRolesMap.get(path);
        if (roles != null
                && !CollectionUtil.containsAny(YuContextHolder.getYuContext().getClientUser().getRoles(), pathRolesMap.get(path))) {
            throw new AccessDeniedException(MessageConstant.PERMISSION_DENIED);
        }
    }

    private String getPath(HttpServletRequest httpServletRequest) {
        String method = httpServletRequest.getMethod();
        String bestMatchingPattern = (String) httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return bestMatchingPattern + "[" + method + "]";
    }

    /**
     * 端点鉴权处理
     *
     * @param path path
     */
    private void endpointPermissionHandler(String path) {
        new YuContextHolder(populateYuContext());
        if (YuContextHolder.getYuContext() != null) {
            // 不是超级管理员（超级管理员不走 权限校验）
            if (YuContextHolder.getYuContext().getClientUser() != null
                    && !multiTenantProperties.getTenants().get(YuContextHolder.getTenantId()).getAdmins().contains(YuContextHolder.getYuContext().getClientUser().getUsername())) {
                checkPathPermission(path);
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        endpointPermissionHandler(getPath(request));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}

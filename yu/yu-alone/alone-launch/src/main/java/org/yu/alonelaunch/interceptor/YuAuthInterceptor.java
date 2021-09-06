package org.yu.alonelaunch.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.yu.alonelaunch.security.constant.MessageConstant;
import org.yu.alonelaunch.security.pojo.SecurityUser;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.dto.LoginUser;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * YU 请求拦截器，用于处理 YuContextHolder填充、端点权限控制
 *
 * @author wangxd
 * @date 2021-09-05
 */
public class YuAuthInterceptor implements HandlerInterceptor {
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        YuContextHolder.clearContext();
    }

    /**
     * 存放 不同tenant的 地址权限 对应关系
     * 不同租户的 端点 访问权限控制 数据结构
     */
    private static Map<Integer, Map<String, Set<String>>> tenantPathRolesMap = new HashMap<>(2);

    @PostConstruct
    public void init() {
        Map<String, Set<String>> pathRolesMap = new HashMap<>(4);
        pathRolesMap.put("/user/{id}[GET]", Stream.of("XXX", "SSS").collect(Collectors.toSet()));
        tenantPathRolesMap.put(1000, pathRolesMap);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        new YuContextHolder(populateYuContext());
        if (YuContextHolder.getYuContext() != null) {
            checkPathPermission(request);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    private YuContext populateYuContext() {
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
            loginUser.setRoles(authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
            YuContext yuContext = new YuContext();
            yuContext.setClientUser(loginUser);
            return yuContext;
        }
        return null;
    }

    private void checkPathPermission(HttpServletRequest httpServletRequest) {
        String path = getPath(httpServletRequest);
        Map<String, Set<String>> pathRolesMap = tenantPathRolesMap.get(YuContextHolder.getTenantId());
        Set<String> roles = pathRolesMap.get(path);
        if (roles != null
                && roles.contains(path)
                && !CollectionUtil.containsAny(YuContextHolder.getYuContext().getClientUser().getRoles(),pathRolesMap.get(path))) {
            throw new AccessDeniedException(MessageConstant.PERMISSION_DENIED);
        }
    }

    private String getPath(HttpServletRequest httpServletRequest) {
        String method = httpServletRequest.getMethod();
        String bestMatchingPattern = (String) httpServletRequest.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return bestMatchingPattern + "[" + method + "]";
    }
}

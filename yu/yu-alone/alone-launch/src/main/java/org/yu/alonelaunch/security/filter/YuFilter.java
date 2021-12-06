package org.yu.alonelaunch.security.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.yu.alonelaunch.log.domain.LogEndpointDO;
import org.yu.alonelaunch.log.service.LogEndpointService;
import org.yu.alonelaunch.security.constant.MessageConstant;
import org.yu.alonelaunch.security.pojo.SecurityUser;
import org.yu.alonelaunch.security.util.TenantUtil;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.dto.LoginUser;
import org.yu.common.core.event.EndpointAccessEvent;
import org.yu.common.core.event.EndpointLogEvent;
import org.yu.common.multidb.properties.MultiTenantProperties;
import org.yu.serve.system.module.endpoint.service.EndpointService;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author wangxd
 * @date 2021-11-30 17:59
 */
@Component
public class YuFilter extends OncePerRequestFilter {
    @Resource
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    private final EndpointService endpointService;
    private final LogEndpointService logEndpointService;

    @Resource
    private MultiTenantProperties multiTenantProperties;

    /**
     * 权限路径 map
     * 存放 不同tenant的 地址权限 对应关系
     * 不同租户的 端点 访问权限控制 数据结构
     */
    private static Map<String, Map<String, Set<String>>> tenantAccessPathMap;
    /**
     * 日志路径 map
     */
    private static Map<String, Set<String>> tenantLogPathMap;

    public YuFilter(EndpointService endpointService, LogEndpointService logEndpointService) {
        this.endpointService = endpointService;
        this.logEndpointService = logEndpointService;
    }

    public void init() {
        //endpointService = SpringUtil.getBean(EndpointService.class);
        //logEndpointService = SpringUtil.getBean(LogEndpointService.class);
        tenantAccessPathMap = new HashMap<>(multiTenantProperties.getTenants().size());
        tenantLogPathMap = new HashMap<>(multiTenantProperties.getTenants().size());
        multiTenantProperties.getTenants().forEach((tenantId, value) -> {
            YuContext yuContext = new YuContext();
            new YuContextHolder(yuContext);
            YuContextHolder.getYuContext().setTenantId(tenantId);
            tenantAccessPathMap.put(tenantId, endpointService.getAccessEndpoints());
            tenantLogPathMap.put(tenantId, endpointService.getLogEndpoints());
            YuContextHolder.clearContext();
        });
    }

    @EventListener(classes = EndpointAccessEvent.class)
    public void accessListener() {
        // 此处如果改为异步执行， tenantId 需要传入，不能直接从 YuContextHolder中获取(使用阿里 TtlExecutors 解决)
        tenantAccessPathMap.put(YuContextHolder.getTenantId(), endpointService.getAccessEndpoints());
    }

    @EventListener(classes = EndpointLogEvent.class)
    public void logListener() {
        // 此处如果改为异步执行， tenantId 需要传入，不能直接从 YuContextHolder中获取(使用阿里 TtlExecutors 解决)
        tenantLogPathMap.put(YuContextHolder.getTenantId(), endpointService.getLogEndpoints());
    }

    private YuContext populateYuContext() {
        YuContext yuContext = new YuContext();
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

            yuContext.setClientUser(loginUser);
        } else {
            yuContext.setTenantId(TenantUtil.getTenantId());
        }

        return yuContext;
    }

    private void checkPathPermission(HttpServletRequest httpServletRequest) {
        String path = getPath(httpServletRequest);
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

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);
        long start = System.currentTimeMillis();
        try {
            // TODO 此方法会执行两遍，会影响效率
            requestMappingHandlerMapping.getHandler(httpServletRequest);
            new YuContextHolder(populateYuContext());
            if (YuContextHolder.getYuContext() != null) {
                // 不是超级管理员（超级管理员不走 权限校验）
                if (YuContextHolder.getYuContext().getClientUser() != null
                        && !multiTenantProperties.getTenants().get(YuContextHolder.getTenantId()).getAdmins().contains(YuContextHolder.getYuContext().getClientUser().getUsername())) {
                    checkPathPermission(httpServletRequest);
                }
            }
            filterChain.doFilter(requestWrapper, responseWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //需要记录日志
            if (tenantLogPathMap.get(YuContextHolder.getTenantId()).contains(getPath(requestWrapper))) {
                String request = new String(requestWrapper.getContentAsByteArray());
                String response = new String(responseWrapper.getContentAsByteArray());
                LogEndpointDO logEndpointDO = LogEndpointDO.builder()
                        .username(YuContextHolder.getYuContext().getClientUser().getUsername())
                        .userId(YuContextHolder.getYuContext().getClientUser().getId())
                        .ip(ServletUtil.getClientIP(requestWrapper))
                        .method(requestWrapper.getMethod())
                        .handler(requestWrapper.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE).toString())
                        .pattern((String) requestWrapper.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE))
                        .url(requestWrapper.getRequestURL().toString())
                        .request(StrUtil.isBlank(request) ? null : request)
                        .response(StrUtil.isBlank(response) ? null : response)
                        .httpStatus(responseWrapper.getStatus())
                        .time((int) (System.currentTimeMillis() - start))
                        .build();
                logEndpointService.asyncSave(logEndpointDO);
            }
            YuContextHolder.clearContext();
            // Do not forget this line after reading response content or actual response will be empty!
            responseWrapper.copyBodyToResponse();
        }
    }
}

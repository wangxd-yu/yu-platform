package org.yu.alonelaunch.interceptor;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.yu.alonelaunch.constant.MessageConstant;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.event.EndpointAccessEvent;
import org.yu.common.multidb.config.EnableMultiDatabase;
import org.yu.serve.system.module.endpoint.service.EndpointService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangxd
 * @date 2021-12-22 8:59
 */
@Component
@ConditionalOnMissingBean(annotation = EnableMultiDatabase.class)
public class YuAuthInterceptorAlone implements YuAuthInterceptor {
    @Resource
    private EndpointService endpointService;

    @Value("${yu.admins:admin}")
    private List<String> admins;

    /**
     * 权限路径 map
     * 存放 不同tenant的 地址权限 对应关系
     * 不同租户的 端点 访问权限控制 数据结构
     */
    private static Map<String, Set<String>> pathRolesMap;

    @Override
    public void init() {
        YuContext yuContext = new YuContext();
        new YuContextHolder(yuContext);
        pathRolesMap = endpointService.getAccessEndpoints();
        YuContextHolder.clearContext();
    }

    @EventListener(classes = EndpointAccessEvent.class)
    public void accessListener() {
        // 此处如果改为异步执行， tenantId 需要传入，不能直接从 YuContextHolder中获取(使用阿里 TtlExecutors 解决)
        pathRolesMap = endpointService.getAccessEndpoints();
    }

    private void checkPathPermission(String path) {
        Set<String> roles = pathRolesMap == null ? null : pathRolesMap.get(path);
        if (roles != null
                && !CollectionUtil.containsAny(YuContextHolder.getYuContext().getSecurityUser().getRoles(), pathRolesMap.get(path))) {
            throw new AccessDeniedException(MessageConstant.PERMISSION_DENIED);
        }
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
            if (YuContextHolder.getYuContext().getSecurityUser() != null
                    && !admins.contains(YuContextHolder.getYuContext().getSecurityUser().getUsername())) {
                checkPathPermission(path);
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        endpointPermissionHandler(getPath(request));
        return YuAuthInterceptor.super.preHandle(request, response, handler);
    }
}

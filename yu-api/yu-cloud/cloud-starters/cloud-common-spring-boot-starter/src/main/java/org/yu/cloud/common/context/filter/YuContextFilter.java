package org.yu.cloud.common.context.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.lang.Nullable;
import org.springframework.web.filter.OncePerRequestFilter;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.dto.SecurityUser;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * @author wangxd
 * @date 2020-11-27 10:04
 */
@ConditionalOnClass(Filter.class)
public class YuContextFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @Nullable HttpServletResponse httpServletResponse, @Nullable FilterChain filterChain) throws ServletException, IOException {
        YuContext yuContext = new YuContext();
        String userStr = httpServletRequest.getHeader("user");
        if (StrUtil.isNotBlank(userStr)) {
            JSONObject userJsonObject = new JSONObject(userStr);
            SecurityUser securityUser = new SecurityUser();
            securityUser.setId(userJsonObject.getStr("id"));
            securityUser.setDeptNo(userJsonObject.getStr("deptNo"));
            securityUser.setTenantId(userJsonObject.getStr("tenantId"));
            securityUser.setClientId(userJsonObject.getStr("client_id"));
            securityUser.setUsername(userJsonObject.getStr("user_name"));
            securityUser.setRoles(new HashSet<>(Convert.toList(String.class, userJsonObject.get("authorities"))));

            yuContext.setTenantId(securityUser.getTenantId());
            yuContext.setClientId(securityUser.getClientId());
            yuContext.setSecurityUser(securityUser);
        } else {
            String tenantId = httpServletRequest.getHeader("tenantId");
            if (StrUtil.isNotBlank(tenantId)) {
                yuContext.setTenantId(tenantId);
            }
        }

        try (YuContextHolder ignored = new YuContextHolder(yuContext)) {
            assert filterChain != null;
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}

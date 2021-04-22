package org.yu.common.context.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.dto.LoginUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nullable;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if(StrUtil.isNotBlank(userStr)) {
            JSONObject userJsonObject = new JSONObject(userStr);
            LoginUser loginUser = new LoginUser();
            loginUser.setId(userJsonObject.getLong("id"));
            loginUser.setDeptNo(userJsonObject.getStr("deptNo"));
            loginUser.setTenantId(userJsonObject.getInt("tenantId"));
            loginUser.setClientId(userJsonObject.getStr("client_id"));
            loginUser.setUsername(userJsonObject.getStr("user_name"));
            loginUser.setRoles(Convert.toList(String.class, userJsonObject.get("authorities")));

            yuContext.setTenantId(loginUser.getTenantId());
            yuContext.setClientId(loginUser.getClientId());
            yuContext.setClientUser(loginUser);
        } else {
            String tenantId = httpServletRequest.getHeader("tenantId");
            if(StrUtil.isNotBlank(tenantId)) {
                yuContext.setTenantId(Integer.valueOf(tenantId));
            }
        }

        try (YuContextHolder ignored = new YuContextHolder(yuContext)) {
            assert filterChain != null;
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}

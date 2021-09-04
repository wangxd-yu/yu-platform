package org.yu.alonelaunch.security.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yu.common.core.context.YuContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-09-04
 */
public class TenantUtil {
    public static Integer getTenantId() {
        Integer tenantId = null;
        if(YuContextHolder.getTenantId() != null) {
            tenantId = YuContextHolder.getTenantId();
        } else {
            HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
            tenantId = Integer.parseInt(req.getParameter("tenantId"));
        }
        return tenantId;
    }
}

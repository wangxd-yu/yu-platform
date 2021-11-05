package org.yu.alonelaunch.security.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yu.common.core.context.YuContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-09-04
 */
public class TenantUtil {
    public static String getTenantId() {
        String tenantId;
        if (YuContextHolder.getTenantId() != null) {
            tenantId = YuContextHolder.getTenantId();
        } else {
            HttpServletRequest req = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            tenantId = req.getParameter("tenantId");
        }
        return tenantId;
    }
}

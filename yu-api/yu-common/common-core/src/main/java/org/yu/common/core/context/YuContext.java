package org.yu.common.core.context;

import lombok.Data;
import org.slf4j.MDC;
import org.yu.common.core.dto.SecurityUser;

/**
 * @author wangxd
 * @date 2020-11-27 9:40
 */
@Data
public class YuContext {

    /**
     * 非用户操作时，存放 租户id（系统调用时）
     */
    private String tenantId;

    private String clientId;

    private SecurityUser securityUser;

    private Object jpaQueryFactory;

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
        MDC.put("tenantId", tenantId);
    }

    public String getTenantId() {
        if(securityUser != null) {
            return securityUser.getTenantId();
        }
        return tenantId;
    }

    public String getClientId() {
        return securityUser.getClientId();
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

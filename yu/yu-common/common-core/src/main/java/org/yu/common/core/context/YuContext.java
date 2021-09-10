package org.yu.common.core.context;

import lombok.Data;
import org.yu.common.core.dto.LoginUser;

/**
 * @author wangxd
 * @date 2020-11-27 9:40
 */
@Data
public class YuContext {

    /**
     * 非用户操作时，存放 租户id（系统调用时）
     */
    private Integer tenantId;

    private String clientId;

    private LoginUser clientUser;

    private Object jpaQueryFactory;

    public Integer getTenantId() {
        if(clientUser != null) {
            return clientUser.getTenantId();
        }
        return tenantId;
    }

    public String getClientId() {
        return clientUser.getClientId();
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

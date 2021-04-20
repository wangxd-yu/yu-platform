package org.yu.common.core.context;

import org.yu.common.core.dto.LoginUser;
import lombok.Data;

/**
 * @author wangxd
 * @date 2020-11-27 9:40
 */
@Data
public class YuContext {

    private Integer tenantId;

    private String clientId;

    private LoginUser clientUser;
}

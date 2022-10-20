package org.yu.admin.base.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.yu.admin.base.log.service.LogEndpointService;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.event.EndpointLogEvent;
import org.yu.common.multidb.config.EnableMultiDatabase;
import org.yu.common.multidb.properties.MultiTenantProperties;
import org.yu.serve.system.module.endpoint.service.EndpointService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangxd
 * @date 2021-12-22 9:21
 */
@Component
@ConditionalOnBean(annotation = EnableMultiDatabase.class)
public class LogEndpointFilterMulti extends LogEndpointFilter {

    /**
     * 日志路径 map
     */
    private static Map<String, Map<String, String>> tenantLogPathMap;
    @Resource
    private MultiTenantProperties multiTenantProperties;

    public LogEndpointFilterMulti(EndpointService endpointService, LogEndpointService logEndpointService) {
        super(endpointService, logEndpointService);
    }

    @Override
    public void init() {
        tenantLogPathMap = new HashMap<>(multiTenantProperties.getTenants().size());
        multiTenantProperties.getTenants().forEach((tenantId, value) -> {
            YuContext yuContext = new YuContext();
            new YuContextHolder(yuContext);
            YuContextHolder.getYuContext().setTenantId(tenantId);
            tenantLogPathMap.put(tenantId, endpointService.getLogEndpoints());
            YuContextHolder.clearContext();
        });
    }

    @Override
    public String getEndpointId(String path) {
        if (YuContextHolder.getTenantId() != null) {
            return tenantLogPathMap.get(YuContextHolder.getTenantId()).get(path);
        }
        return null;
    }

    @EventListener(classes = EndpointLogEvent.class)
    public void logListener() {
        // 此处如果改为异步执行， tenantId 需要传入，不能直接从 YuContextHolder中获取(使用阿里 TtlExecutors 解决)
        tenantLogPathMap.put(YuContextHolder.getTenantId(), endpointService.getLogEndpoints());
    }
}

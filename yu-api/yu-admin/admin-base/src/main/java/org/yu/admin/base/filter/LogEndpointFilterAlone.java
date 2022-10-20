package org.yu.admin.base.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.yu.admin.base.log.service.LogEndpointService;
import org.yu.common.core.context.YuContext;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.event.EndpointLogEvent;
import org.yu.common.multidb.config.EnableMultiDatabase;
import org.yu.serve.system.module.endpoint.service.EndpointService;

import java.util.Map;

/**
 * @author wangxd
 * @date 2021-12-22 9:21
 */
@Component
@ConditionalOnMissingBean(annotation = EnableMultiDatabase.class)
public class LogEndpointFilterAlone extends LogEndpointFilter {
    /**
     * 日志路径 map
     */
    private static Map<String, String> logPathMap;

    public LogEndpointFilterAlone(EndpointService endpointService, LogEndpointService logEndpointService) {
        super(endpointService, logEndpointService);
    }

    @Override
    public void init() {
        YuContext yuContext = new YuContext();
        new YuContextHolder(yuContext);
        logPathMap = super.endpointService.getLogEndpoints();
        YuContextHolder.clearContext();
    }

    @Override
    public String getEndpointId(String path) {
        return logPathMap.get(path);
    }

    @EventListener(classes = EndpointLogEvent.class)
    public void logListener() {
        // 此处如果改为异步执行， tenantId 需要传入，不能直接从 YuContextHolder中获取(使用阿里 TtlExecutors 解决)
        logPathMap = endpointService.getLogEndpoints();
    }

}

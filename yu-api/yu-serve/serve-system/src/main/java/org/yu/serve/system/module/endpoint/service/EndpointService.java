package org.yu.serve.system.module.endpoint.service;

import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.endpoint.domain.EndpointDO;
import org.yu.serve.system.module.endpoint.dto.EndpointLessDTO;

import java.util.Map;
import java.util.Set;

/**
 * 端点 service
 *
 * @author wangxd
 * @date 2021-09-09
 */
public interface EndpointService extends DslBaseService<EndpointDO, String> {

    Map<String, Set<String>> getAccessEndpoints();

    Map<String, String> getLogEndpoints();

    Set<EndpointLessDTO> getRequestMappingInfos();

    void changeAccessEnabled(String id, boolean enabled);

    void changeLogEnabled(String id, boolean enabled);
}

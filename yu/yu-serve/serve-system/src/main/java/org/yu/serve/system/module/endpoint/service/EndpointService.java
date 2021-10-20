package org.yu.serve.system.module.endpoint.service;

import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.endpoint.domain.EndpointDO;
import org.yu.serve.system.module.endpoint.dto.EndpointLessDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 端点 service
 *
 * @author wangxd
 * @date 2021-09-09
 */
public interface EndpointService extends DslBaseService<EndpointDO, String> {

    Map<String, Set<String>> getEndpointRoles();

    Set<EndpointLessDTO> getRequestMappingInfos();

    void changeEnabled(String id, boolean enabled);
}

package org.yu.serve.system.module.endpoint.service;

import com.querydsl.core.Tuple;
import org.springframework.stereotype.Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.endpoint.domain.EndpointDO;
import org.yu.serve.system.module.endpoint.domain.QEndpointDO;
import org.yu.serve.system.module.endpoint.repository.EndpointRepository;
import org.yu.serve.system.module.menu.domain.QMenuDO;
import org.yu.serve.system.module.menu.domain.QMenuEndpointDO;
import org.yu.serve.system.module.role.domain.QRoleDO;
import org.yu.serve.system.module.role.domain.QRoleMenuDO;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-09-09
 */
@Service
public class EndpointServiceImpl extends DslBaseServiceImpl<EndpointRepository, EndpointDO, String> implements EndpointService {

    private final QEndpointDO qEndpointDO = QEndpointDO.endpointDO;
    private final QRoleDO qRoleDO = QRoleDO.roleDO;
    private final QMenuEndpointDO qMenuEndpointDO = QMenuEndpointDO.menuEndpointDO;
    private final QRoleMenuDO qRoleMenuDO = QRoleMenuDO.roleMenuDO;

    @Override
    public Map<String, Set<String>> getEndpointRoles() {
        List<Tuple> tupleList = getJPAQueryFactory().select(
                        qEndpointDO.pattern,
                        qEndpointDO.method,
                        qRoleDO.code
                ).from(
                        qEndpointDO
                )
                .leftJoin(qMenuEndpointDO).on(qEndpointDO.id.eq(qMenuEndpointDO.endpointId))
                .leftJoin(qRoleMenuDO).on(qMenuEndpointDO.menuId.eq(qRoleMenuDO.menuId))
                .leftJoin(qRoleDO).on(qRoleMenuDO.roleId.eq(qRoleDO.id))
                .fetch();
        return tupleList.stream().collect(Collectors.groupingBy(
                tuple -> tuple.get(qEndpointDO.pattern) + "[" + tuple.get(qEndpointDO.method) + "]",
                Collectors.mapping(tuple -> tuple.get(qRoleDO.code), Collectors.toSet())
        ));
    }
}

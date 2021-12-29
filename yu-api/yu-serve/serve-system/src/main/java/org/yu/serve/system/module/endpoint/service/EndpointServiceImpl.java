package org.yu.serve.system.module.endpoint.service;

import com.querydsl.core.Tuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.yu.common.core.event.EndpointAccessEvent;
import org.yu.common.core.event.EndpointLogEvent;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.endpoint.domain.EndpointDO;
import org.yu.serve.system.module.endpoint.domain.QEndpointDO;
import org.yu.serve.system.module.endpoint.dto.EndpointLessDTO;
import org.yu.serve.system.module.endpoint.repository.EndpointRepository;
import org.yu.serve.system.module.menu.domain.QMenuEndpointDO;
import org.yu.serve.system.module.role.domain.QRoleDO;
import org.yu.serve.system.module.role.domain.QRoleMenuDO;

import java.util.HashSet;
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

    private static Set<EndpointLessDTO> endpointLessDTOS;

    private final WebApplicationContext applicationContext;

    public EndpointServiceImpl(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Map<String, Set<String>> getAccessEndpoints() {
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
                .where(qEndpointDO.accessEnabled.eq(true))
                .fetch();
        return tupleList.stream().collect(Collectors.groupingBy(
                tuple -> tuple.get(qEndpointDO.pattern) + "[" + tuple.get(qEndpointDO.method) + "]",
                Collectors.mapping(tuple -> tuple.get(qRoleDO.code), Collectors.toSet())
        ));
    }

    @Override
    public Map<String, String> getLogEndpoints() {
        List<Tuple> tupleList = getJPAQueryFactory().select(
                        qEndpointDO.pattern,
                        qEndpointDO.method,
                        qEndpointDO.id
                ).from(qEndpointDO)
                .where(qEndpointDO.logEnabled.eq(true))
                .fetch();
        return tupleList.stream().collect(Collectors.toMap(
                tuple -> tuple.get(qEndpointDO.pattern) + "[" + tuple.get(qEndpointDO.method) + "]",
                tuple -> tuple.get(qEndpointDO.id)
        ));
    }

    @Override
    public Set<EndpointLessDTO> getRequestMappingInfos() {
        if (endpointLessDTOS == null) {
            RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            //获取url与类和方法的对应信息
            endpointLessDTOS = new HashSet<>();
            mapping.getHandlerMethods().forEach((key, value) ->
                    key.getPathPatternsCondition().getPatterns().forEach(pattern ->
                            key.getMethodsCondition().getMethods().forEach(method ->
                                    endpointLessDTOS.add(new EndpointLessDTO(pattern.getPatternString(), method))
                            )
                    )
            );
        }
        return endpointLessDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeAccessEnabled(String id, boolean enabled) {
        getJPAQueryFactory().update(qEndpointDO)
                .set(qEndpointDO.accessEnabled, enabled)
                .where(qEndpointDO.id.eq(id))
                .execute();
        applicationContext.publishEvent(new EndpointAccessEvent(this));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeLogEnabled(String id, boolean enabled) {
        getJPAQueryFactory().update(qEndpointDO)
                .set(qEndpointDO.logEnabled, enabled)
                .where(qEndpointDO.id.eq(id))
                .execute();
        applicationContext.publishEvent(new EndpointLogEvent(this));
    }
}

package org.yu.tenant.service.datasource.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.ServiceMatcher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.tenant.service.api.datasource.domain.DataSourceDO;
import org.yu.tenant.service.api.datasource.event.DataSourceEvent;
import org.yu.tenant.service.datasource.repository.DataSourceRepository;
import org.yu.tenant.service.datasource.service.DataSourceService;

/**
 * 数据源-实现
 *
 * @author wangxd
 * @date 2021-04-02 14:52
 */
@Slf4j
@Service
public class DataSourceServiceImpl extends DslBaseServiceImpl<DataSourceRepository, DataSourceDO, Long> implements DataSourceService {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final ServiceMatcher busServiceMatcher;

    public DataSourceServiceImpl(ApplicationEventPublisher applicationEventPublisher, ServiceMatcher busServiceMatcher) {
        this.applicationEventPublisher = applicationEventPublisher;
        this.busServiceMatcher = busServiceMatcher;
    }

    @Override
    public void enableDataSource(Long datasourceId) {
        //TODO 1、打印日志；2、获取groupId；3、destinationService 按 groupId去发送
        Long groupId = null;
        log.info("[bus][启用DataSource({})]", datasourceId);
        applicationEventPublisher.publishEvent(
                new DataSourceEvent(this, busServiceMatcher.getServiceId(), null,
                        DataSourceEvent.DataSourceEventTypeEnum.ENABLE, groupId, datasourceId));
    }

    @Override
    public void disableDataSource(Long datasourceId) {
        //TODO 1、打印日志；2、获取groupId；3、destinationService 按 groupId去发送
        Long groupId = null;
        log.info("[bus][停用DataSource({})]", datasourceId);
        applicationEventPublisher.publishEvent(
                new DataSourceEvent(this, busServiceMatcher.getServiceId(), null,
                        DataSourceEvent.DataSourceEventTypeEnum.DISABLE, groupId, datasourceId));
    }
}

package org.yu.tenant.service.api.datasource.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.yu.tenant.service.api.datasource.event.DataSourceEvent;
import org.yu.tenant.service.api.datasource.service.DataSourceEventService;

/**
 * 数据源消息事件 监听器
 *
 * @author wangxd
 * @date 2021-04-09
 */
@Slf4j
@Component
public class DataSourceListener implements ApplicationListener<DataSourceEvent> {
    private final DataSourceEventService dataSourceEventService;

    public DataSourceListener(@Nullable DataSourceEventService dataSourceEventService) {
        this.dataSourceEventService = dataSourceEventService;
    }

    @Override
    public void onApplicationEvent(DataSourceEvent dataSourceEvent) {
        log.info("[bus-dataSource]:({})", dataSourceEvent.toString());
        if (dataSourceEventService != null) {
            if (dataSourceEvent.getEventType() == DataSourceEvent.DataSourceEventTypeEnum.ENABLE) {
                dataSourceEventService.enableDataSource(dataSourceEvent.getDatasourceId());
            } else if (dataSourceEvent.getEventType() == DataSourceEvent.DataSourceEventTypeEnum.DISABLE) {
                dataSourceEventService.disableDataSource(dataSourceEvent.getDatasourceId());
            }
        }
    }
}

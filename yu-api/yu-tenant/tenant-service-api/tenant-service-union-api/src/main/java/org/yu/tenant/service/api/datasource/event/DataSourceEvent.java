package org.yu.tenant.service.api.datasource.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * datasource 事件
 *
 * @author wangxd
 * @date 2021-04-07 19:19
 */
@Getter
@Setter
@ToString
public class DataSourceEvent extends RemoteApplicationEvent {
    private DataSourceEventTypeEnum eventType;

    private Long groupId;

    private Long datasourceId;

    /**
     * 序列化
     */
    public DataSourceEvent() {
    }

    public DataSourceEvent(Object source, String originService, String destinationService, DataSourceEventTypeEnum eventType, Long groupId, Long datasourceId) {
        super(source, originService, destinationService);
        this.eventType = eventType;
        this.groupId = groupId;
        this.datasourceId = datasourceId;
    }

    public enum DataSourceEventTypeEnum {
        /**
         * 启用
         */
        ENABLE,
        /**
         * 停用
         */
        DISABLE
    }
}

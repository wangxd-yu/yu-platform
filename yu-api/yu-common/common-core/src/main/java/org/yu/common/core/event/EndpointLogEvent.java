package org.yu.common.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wangxd
 * @date 2021-11-30 11:00
 */
public class EndpointLogEvent extends ApplicationEvent {
    public EndpointLogEvent(Object source) {
        super(source);
    }
}

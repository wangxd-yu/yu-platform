package org.yu.common.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wangxd
 * @date 2021-11-03 11:00
 */
public class EndpointAccessEvent extends ApplicationEvent {
    public EndpointAccessEvent(Object source) {
        super(source);
    }
}

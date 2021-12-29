package org.yu.alonelaunch.log.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yu.alonelaunch.log.domain.LogEndpointDO;
import org.yu.alonelaunch.log.repository.LogEndpointRepository;
import org.yu.alonelaunch.log.service.LogEndpointService;
import org.yu.common.querydsl.service.DslBaseServiceImpl;

/**
 * @author wangxd
 * @date 2021-12-01 22:20
 */
@Slf4j
@Service
public class LogEndpointServiceImpl extends DslBaseServiceImpl<LogEndpointRepository, LogEndpointDO, String> implements LogEndpointService {
    @Override
    public void asyncSave(LogEndpointDO logEndpointDO) {
        super.save(logEndpointDO);
    }
}

package org.yu.admin.base.log.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yu.admin.base.log.domain.LogEndpointDO;
import org.yu.admin.base.log.repository.LogEndpointRepository;
import org.yu.admin.base.log.service.LogEndpointService;
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

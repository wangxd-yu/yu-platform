package org.yu.admin.base.log.service.impl;

import org.springframework.stereotype.Service;
import org.yu.admin.base.log.domain.LogLoginDO;
import org.yu.admin.base.log.repository.LogLoginRepository;
import org.yu.admin.base.log.service.LogLoginService;
import org.yu.common.querydsl.service.DslBaseServiceImpl;

/**
 * @author wangxd
 * @date 2021-11-09 22:25
 */
@Service
public class LogLoginServiceImpl extends DslBaseServiceImpl<LogLoginRepository, LogLoginDO, String> implements LogLoginService {
    @Override
    public void asyncSave(LogLoginDO logLoginDO) {
        save(logLoginDO);
    }
}

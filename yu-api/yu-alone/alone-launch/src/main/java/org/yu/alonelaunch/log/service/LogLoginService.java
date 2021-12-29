package org.yu.alonelaunch.log.service;

import org.springframework.scheduling.annotation.Async;
import org.yu.alonelaunch.log.domain.LogLoginDO;
import org.yu.common.querydsl.service.DslBaseService;

/**
 * @author wangxd
 * @date 2021-11-09 22:23
 */
public interface LogLoginService extends DslBaseService<LogLoginDO, String> {
    /**
     * 异步保存 日志
     *
     * @param logLoginDO domain对象
     */
    @Async
    void asyncSave(LogLoginDO logLoginDO);
}

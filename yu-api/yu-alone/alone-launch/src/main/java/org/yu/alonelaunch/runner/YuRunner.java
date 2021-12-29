package org.yu.alonelaunch.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.yu.alonelaunch.filter.LogEndpointFilter;
import org.yu.alonelaunch.interceptor.YuAuthInterceptor;

import javax.annotation.Resource;

/**
 * @author wangxd
 * @date 2021-12-03 21:29
 */
@Component
public class YuRunner implements ApplicationRunner {

    @Resource
    private YuAuthInterceptor yuAuthInterceptor;

    @Resource
    private LogEndpointFilter logEndpointFilter;

    @Override
    public void run(ApplicationArguments args) {
        yuAuthInterceptor.init();
        logEndpointFilter.init();
    }
}

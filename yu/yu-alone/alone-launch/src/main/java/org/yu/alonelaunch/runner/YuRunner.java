package org.yu.alonelaunch.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.yu.alonelaunch.security.filter.YuFilter;

import javax.annotation.Resource;

/**
 * @author wangxd
 * @date 2021-12-03 21:29
 */
@Component
public class YuRunner implements ApplicationRunner {

    @Resource
    private YuFilter yuFilter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        yuFilter.init();
    }
}

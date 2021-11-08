package org.yu.common.web.async;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;

/**
 * @author wangxd
 * @date 2021-11-08 22:55
 */
@Configuration
public class AsyncExecutorConfig {
    @Resource
    private AsyncExecutorProperties asyncExecutorProperties;

    @Bean
    @Primary
    @ConditionalOnBean(annotation = EnableAsync.class)
    public Executor getAsyncExecutor() {
        //创建线程池包装对象
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置线程池参数
        taskExecutor.setCorePoolSize(asyncExecutorProperties.getCorePoolSize());
        taskExecutor.setMaxPoolSize(asyncExecutorProperties.getMaxPoolSize());
        taskExecutor.setKeepAliveSeconds(asyncExecutorProperties.getKeepAliveSeconds());
        taskExecutor.setAllowCoreThreadTimeOut(asyncExecutorProperties.isAllowCoreThreadTimeOut());
        taskExecutor.setQueueCapacity(asyncExecutorProperties.getQueueCapacity());
        taskExecutor.setThreadNamePrefix(asyncExecutorProperties.getThreadNamePrefix());
        taskExecutor.setRejectedExecutionHandler(asyncExecutorProperties.getRejectedExecutionHandler().getRejectedExecutionHandler());
        taskExecutor.initialize();
        return taskExecutor;
    }
}

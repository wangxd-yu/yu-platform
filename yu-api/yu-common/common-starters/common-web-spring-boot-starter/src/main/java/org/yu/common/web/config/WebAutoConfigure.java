package org.yu.common.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yu.common.web.async.AsyncExecutorProperties;
import org.yu.common.web.exception.handler.GlobalExceptionHandler;
import org.yu.common.web.util.QueryDslUtil;

/**
 * @author wangxd
 * @date 2021-11-08 22:09
 */
@Configuration
@EnableConfigurationProperties(AsyncExecutorProperties.class)
public class WebAutoConfigure {

    @Bean
    public GlobalExceptionHandler getGlobalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public QueryDslUtil getQueryDslUtil() {
        return new QueryDslUtil();
    }

    /*@Bean
    public AsyncExecutorConfig asyncExecutorConfig() {
        return new AsyncExecutorConfig();
    }*/
}

package org.yu.alonelaunch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yu.alonelaunch.interceptor.YuAuthInterceptor;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-09-05
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public YuAuthInterceptor yuAuthInterceptor() {
        return new YuAuthInterceptor();
    }

    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE");

    }*/

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.yuAuthInterceptor()).addPathPatterns("/**");
    }
}

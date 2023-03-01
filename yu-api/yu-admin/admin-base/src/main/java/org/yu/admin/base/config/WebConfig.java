package org.yu.admin.base.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.yu.admin.base.filter.LogEndpointFilter;
import org.yu.admin.base.interceptor.YuAuthInterceptor;
import org.yu.serve.file.util.FilePathUtil;

import javax.annotation.Resource;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-09-05
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    public YuAuthInterceptor yuAuthInterceptor;

    @Resource
    public LogEndpointFilter logEndpointFilter;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                // SpringBoot2.4.0 [allowedOriginPatterns]代替[allowedOrigins]
                .allowedOriginPatterns("*")
                //设置预检请求的缓存时间的，单位为秒。只是建议缓存时间，而实际上，浏览器会根据自身的规则来判断是否缓存响应，因此并不能保证在指定的时间内缓存响应
                .maxAge(3600L)
                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
        ;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        log.info("register global exception filter");
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(logEndpointFilter);
        bean.addUrlPatterns("/*");
        // 优先级最高
        bean.setOrder(Integer.MIN_VALUE);
        return bean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(yuAuthInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").setCachePeriod(0);
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler(FilePathUtil.getStaticAccessPath()).addResourceLocations(FilePathUtil.getUploadFolder());
    }
}

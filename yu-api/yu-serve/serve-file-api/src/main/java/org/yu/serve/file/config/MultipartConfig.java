package org.yu.serve.file.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * @author https://blog.csdn.net/llibin1024530411/article/details/79474953
 * @date 2018-12-28
 */
@Slf4j
@Configuration
@EnableJpaRepositories("org.yu.serve.file")
@EntityScan("org.yu.serve.file")
@ComponentScan(value = {"org.yu.serve.file"})
public class MultipartConfig {
    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/.upload/file/tmp";
        log.info("location : " + location);
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        return factory.createMultipartConfig();
    }
}
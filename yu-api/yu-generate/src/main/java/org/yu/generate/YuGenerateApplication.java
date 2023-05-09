package org.yu.generate;

import cn.hutool.core.io.IoUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author wangxd
 */
@SpringBootApplication
public class YuGenerateApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(YuGenerateApplication.class, args);

        ClassPathResource classPathResource = new ClassPathResource("template/generator/api/Controller.ftl");
        try (InputStream inputStream = classPathResource.getInputStream()) {
            String hello = IoUtil.read(inputStream, StandardCharsets.UTF_8);
            System.out.println(hello);
        }
    }

}

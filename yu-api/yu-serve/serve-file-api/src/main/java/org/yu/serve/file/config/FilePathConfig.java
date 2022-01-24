package org.yu.serve.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.yu.serve.file.enums.FilePathEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangxd
 * @date 2022-01-05
 */
@Data
@Component
@ConfigurationProperties(prefix = "file-path-config")
public class FilePathConfig {
    private final String root = "upload";

    private String staticAccessPath = String.format("/%s/**", root);
    /**
     * /opt/sdsesFiles/
     */
    private String uploadPath = System.getProperty("user.dir") + "/" + root + "/";
    /**
     * file:/opt/sdsesFiles/
     */
    private String uploadFolder = "file:" + uploadPath;

    private Map<String, FilePathModelConfig> pathMap = new HashMap<>(8);

    public void setPathMap(Map<String, FilePathModelConfig> pathMap) {
        /*
            对已上线代码的兼容
            将 字符： - 转换为 _    (中划线 -> 下划线)
         */
        if (pathMap != null) {
            pathMap.forEach((k, v) -> this.pathMap.put(k.replace("-", "_"), v));
        }
    }

    /**
     * 新增文件地址
     *
     * @param filePathEnum filePathEnum
     */
    public void addPathMap(FilePathEnum filePathEnum) {
        FilePathModelConfig filePathModelConfig = new FilePathModelConfig(filePathEnum.getPath(), filePathEnum.getType().getType());
        this.pathMap.put(filePathEnum.getName(), filePathModelConfig);
    }
}
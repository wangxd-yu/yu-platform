package org.yu.serve.file.config;

import lombok.Data;

/**
 * @author wangxd
 * @date 2022-01-05
 */
@Data
public class FilePathModelConfig {
    /**
     * 路径
     */
    private String path;
    /**
     * 类型
     * 0: 默认路径（path）
     * 1: 按年存放
     * 2: 按月存放
     * 3: 按日存放
     * 4: 按小时存放
     * 5: 按分钟存放
     * 6: 按秒存放
     */
    private int type;

    public FilePathModelConfig() {
    }

    public FilePathModelConfig(String path, int type) {
        this.path = path;
        this.type = type;
    }
}

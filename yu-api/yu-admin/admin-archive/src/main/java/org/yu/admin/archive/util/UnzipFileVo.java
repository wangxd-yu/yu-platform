package org.yu.admin.archive.util;

import lombok.Data;

/**
 * 文件解析对象
 *
 * @author Yuanqiang.Zhang
 * @since 2022/7/12
 */
@Data
public class UnzipFileVo {

    /**
     * 类型：0-文件夹；1-文件
     */
    private Integer type;

    /**
     * 文件路径（如：src/main/java/com/zyq/entity/User.java）
     */
    private String path;

    /**
     * 文件内容
     */
    private String content;

}
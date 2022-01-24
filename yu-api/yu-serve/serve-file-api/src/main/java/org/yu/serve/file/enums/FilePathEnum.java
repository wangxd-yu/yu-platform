package org.yu.serve.file.enums;

/**
 * @author wangxd
 * @date 2022-01-05
 */
public interface FilePathEnum {
    /**
     * 获取枚举名称
     */
    String getName();
    /**
     * 获取路径
     * @return  路径
     */
    String getPath();

    /**
     * 获取存放文件后缀
     * @return  后缀
     */
    FileSuffix getSuffix();

    /**
     * 获取保存目录类型
     * @return  目录类型
     */
    FilePathType getType();
}
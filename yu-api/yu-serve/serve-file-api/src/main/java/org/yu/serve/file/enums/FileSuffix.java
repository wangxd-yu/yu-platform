package org.yu.serve.file.enums;

/**
 * 后缀
 * @author wangxd
 * @date 2022-01-05
 */
public enum FileSuffix {
    JPG(".jpg"),
    PNG(".png"),
    XLSX(".xlsx");

    String suffix;

    FileSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}

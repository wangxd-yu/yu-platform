package org.yu.serve.file.enums;

import java.util.Arrays;

/**
 * @author wangxd
 * @date 2022-01-05
 */
public enum FilePathType {
    NONE(0, ""),
    YEAR(1, "yyyy/"),
    MONTH(2, "yyyy/MM/"),
    DAY(3, "yyyy/MM/dd/"),
    HOUR(4, "yyyy/MM/dd/HH/"),
    MINUTE(5, "yyyy/MM/dd/HH/mm/"),
    SECOND(6, "yyyy/MM/dd/HH/mm/ss/");

    private final int type;
    private final String pattern;

    FilePathType(int type, String pattern) {
        this.type = type;
        this.pattern = pattern;
    }

    public int getType() {
        return type;
    }

    public String getPattern() {
        return pattern;
    }

    public FilePathType getPattern(int type) {
        return Arrays.stream(FilePathType.values())
                .filter(filePathType -> filePathType.getType() == type)
                .findFirst().orElse(null);
    }
}

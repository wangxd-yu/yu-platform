package com.yu.common.querydsl.query.enums;

/**
 * Sdses日期类
 *
 * @author wangxd
 * @date 2020-03-01
 */
public enum YuDateTimeEnum {
    //日期
    DATE("DATE_FORMAT({0},'%Y-%m-%d')", "yyyy-MM-dd"),
    //时间
    DATETIME("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%s')", "yyyy-MM-dd HH:mm:ss");

    private final String template;
    private final String format;

    YuDateTimeEnum(String template, String format) {
        this.template = template;
        this.format = format;
    }

    public String getTemplate() {
        return template;
    }

    public String getFormat() {
        return format;
    }
}

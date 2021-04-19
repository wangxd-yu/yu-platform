package com.yu.common.querydsl.query.enums;

/**
 * Sdses操作符
 * @author wangxd
 * @date 2020-03-01
 */
public enum YuOperatorEnum {
    EQUAL("eq",null),
    NOT_EQUAL("ne", null),
    GREATER_THAN("gt", null),
    GREATER_OR_EQUAL("goe", null),
    LESS_THAN("lt",null),
    LESS_OR_EQUAL("loe",null),
    INNER_LIKE("like","%{}%"),
    NOT_INNER_LIKE("notLike","%{}%"),
    START_WITH("like","{}%"),
    END_WITH("like","%{}"),
    IN("in",null);

    private String name;

    private String format;

    YuOperatorEnum(String name, String format) {
        this.name = name;
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public String getFormat() {
        return format;
    }
}

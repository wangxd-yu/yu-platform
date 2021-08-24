package org.yu.common.querydsl.query.enums;

/**
 * 操作符
 *
 * @author wangxd
 * @date 2020-03-01
 */
public enum YuOperatorEnum {
    //相等
    EQUAL("eq", null),
    //不相等
    NOT_EQUAL("ne", null),
    //大于
    GREATER_THAN("gt", null),
    //大于等于
    GREATER_OR_EQUAL("goe", null),
    //小于
    LESS_THAN("lt", null),
    //小于等于
    LESS_OR_EQUAL("loe", null),
    //like
    INNER_LIKE("like", "%{0}%"),
    //not like
    NOT_INNER_LIKE("notLike", "%{0}%"),
    //start with
    START_WITH("like", "{0}%"),
    //end with
    END_WITH("like", "%{0}"),
    //in
    IN("in", null);

    private final String name;

    private final String format;

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

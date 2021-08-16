package org.yu.common.querydsl.api;

import java.util.Arrays;

/**
 * @author wangxd
 * @date 2021-08-11 9:45
 */
public enum MultiDataTypeEnum {
    /**
     * 分页
     */
    PAGE,
    /**
     * 列表
     */
    LIST,
    /**
     * 树
     */
    TREE;

    public static MultiDataTypeEnum getByName(String name) {
        return Arrays.stream(MultiDataTypeEnum.values()).filter(item ->item.name().equals(name)).findAny().orElse(null);
    }
}

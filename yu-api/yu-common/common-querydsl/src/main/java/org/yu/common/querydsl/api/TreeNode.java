package org.yu.common.querydsl.api;

import java.util.List;
import java.util.Objects;

/**
 * @author wangxd
 * @date 2021-08-16 8:32
 */
public interface TreeNode<T> {
    /**
     * 排序
     * @return
     */
    Integer getSort();

    String getId();

    String getPid();

    default boolean isRoot() {
        return Objects.equals("0", getPid());
    }

    default boolean isNotRoot() {
        return !Objects.equals("0", getPid());
    }

    List<T> getChildren();

    void setChildren(List<T> children);
}

package org.yu.common.querydsl.api;

import java.util.List;

/**
 * @author wangxd
 * @date 2021-08-16 8:32
 */
public interface TreeNode<T> {
    String getId();

    String getPid();

    default boolean isRoot() {
        return "0".equals(getPid());
    }

    List<T> getChildren();

    void setChildren(List<T> children);
}

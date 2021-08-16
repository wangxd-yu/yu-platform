package org.yu.common.querydsl.api;

import lombok.Data;

import java.util.List;

/**
 * @author wangxd
 * @date 2021-08-15 16:41
 */
@Data
public class TreeDTO<T> {
    private String id;

    private String pid;

    private List<T> children;
}

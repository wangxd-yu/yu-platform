package org.yu.generate.dto;

import lombok.Data;

/**
 * @author wangxd
 * @date 2023-04-08 23:33
 */
@Data
public class TableDto {
    /**
     * 数据库字段名称 name
     **/
    private String name;
    /**
     * 数据库字段注释
     **/
    private String comment;
}

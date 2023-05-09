package org.yu.generate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author wangxd
 * @date 2023-04-08 23:33
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ColumnDto {
    /**
     * 数据库字段名称 name
     **/
    @Id
    private String columnName;
    /**
     * 数据库字段类型
     **/
    private String dataType;
    /**
     * 数据库字段注释
     **/
    private String columnComment;
}

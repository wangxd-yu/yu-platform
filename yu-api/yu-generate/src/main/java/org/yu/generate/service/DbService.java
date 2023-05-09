package org.yu.generate.service;

import org.yu.generate.dto.ColumnDto;

import java.util.List;

/**
 * @author wangxd
 * @date 2023-04-07 23:51
 */
public interface DbService {
    /**
     * 获取所有table
     *
     * @return /
     */
    Object getTables();

    /**
     * 得到数据表的元数据
     *
     * @param tableName 表名
     * @return /
     */
    List<ColumnDto> getColumns(String tableName);
}

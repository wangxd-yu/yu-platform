package org.yu.generate.service;

import java.util.List;
import java.util.Map;

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
    List<Map<String, Object>> getColumns(String tableName);

}

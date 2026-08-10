package org.yu.generate.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class DbServiceJdbcImpl implements DbService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public List<Map<String, Object>> getTables() {
        String sql = "select * from information_schema.tables " +
                "where table_schema = (select database()) " +
                "order by create_time desc";
        return executeQuery(sql);
    }

    @Override
    @Transactional
    public List<Map<String, Object>> getColumns(String tableName) {
        // 防止传递空的tableName
        if (tableName == null || tableName.isEmpty()) {
            return Collections.emptyList();
        }

        String sql = "select * from information_schema.columns " +
                "where table_name = ? and table_schema = (select database()) " +
                "order by ordinal_position";
        return executeQuery(sql, tableName);
    }

    // 通用查询方法，适用于没有参数的 SQL 查询
    private List<Map<String, Object>> executeQuery(String sql, Object... params) {
        return jdbcTemplate.queryForList(sql, params);
    }
}

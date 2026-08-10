package org.yu.generate.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangxd
 * @date 2023-04-08 23:37
 */

public class DbServiceImpl implements DbService {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Map<String, Object>> getTables() {
        String sql = "select * " +
                "from information_schema.tables " +
                "where table_schema = (select database()) " +
                "order by create_time desc";
        Query query = em.createNativeQuery(sql);
        return executeNativeQuery(sql, query);
    }

    private List<Map<String, Object>> executeNativeQuery(String sql, Query query) {

        List<Object[]> resultList = query.getResultList();

        // 获取列名
        List<String> columnNames = getColumnNames(sql);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> rowMap = new HashMap<>();
            for (int i = 0; i < row.length; i++) {
                rowMap.put(columnNames.get(i), row[i]);
            }
            result.add(rowMap);
        }

        return result;
    }

    private List<String> getColumnNames(String sql) {
        List<String> columnNames = new ArrayList<>();
        try {
            // 使用 doWork() 方法来获取 ResultSetMetaData
            em.unwrap(org.hibernate.Session.class).doWork(connection -> {
                try {
                    java.sql.Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(sql);
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        columnNames.add(metaData.getColumnName(i));
                    }
                    resultSet.close();
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnNames;
    }

    @Override
    @Transactional
    public List<Map<String, Object>> getColumns(String tableName) {
        // 使用预编译防止sql注入
        String sql = "select * from information_schema.columns " +
                "where table_name = ? and table_schema = (select database()) order by ordinal_position";
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, tableName); // 使用位置参数来替换命名参数
        return executeNativeQuery(sql, query);
       /*Query query = em.createNativeQuery(sql, ColumnDto.class);
        query.setParameter(1, tableName);
        return query.getResultList();*/
    }

}

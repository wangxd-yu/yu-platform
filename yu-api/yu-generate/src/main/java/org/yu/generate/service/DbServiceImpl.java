package org.yu.generate.service;

import org.springframework.stereotype.Service;
import org.yu.generate.dto.ColumnDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author wangxd
 * @date 2023-04-08 23:37
 */
@Service
public class DbServiceImpl implements DbService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Object getTables() {
        // 使用预编译防止sql注入
        String sql = "select table_name ,create_time , engine, table_collation, table_comment from information_schema.tables " +
                "where table_schema = (select database()) " +
                "order by create_time desc";
        Query query = em.createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public List<ColumnDto> getColumns(String tableName) {
        // 使用预编译防止sql注入
        String sql = "select column_name, is_nullable, data_type, column_comment, column_key, extra from information_schema.columns " +
                "where table_name = ? and table_schema = (select database()) order by ordinal_position";
        Query query = em.createNativeQuery(sql, ColumnDto.class);
        query.setParameter(1, tableName);
        return query.getResultList();
    }
}

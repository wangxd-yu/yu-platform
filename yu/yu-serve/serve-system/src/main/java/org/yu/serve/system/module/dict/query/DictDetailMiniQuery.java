package org.yu.serve.system.module.dict.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuJoin;
import org.yu.common.querydsl.query.annotation.YuJoinColumn;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.serve.system.module.dict.domain.DictDO;
import org.yu.serve.system.module.dict.domain.DictDetailDO;

/**
 * @author wangxd
 * @date 2020-12-02 19:22
 */
@Data
@YuQuery(domain = DictDetailDO.class, joins = {
        @YuJoin(domain = DictDO.class, type = YuJoin.Type.LEFT_JOIN, columns = {
                @YuJoinColumn(column = "id", relationColumn = "dictId")
        })
})
public class DictDetailMiniQuery {
    @YuQueryColumn(domain = DictDO.class, propName = "code")
    private String dictCode;
}

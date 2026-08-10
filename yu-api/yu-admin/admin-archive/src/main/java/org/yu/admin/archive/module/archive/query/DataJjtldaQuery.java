package org.yu.admin.archive.module.archive.query;

import lombok.Data;
import org.yu.admin.archive.module.archive.domain.DataJjtldaDO;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;

/**
 * @author wangxd
 * @date 2024-12-21 16:10
 */
@Data
@YuQuery(domain = DataJjtldaDO.class)
public class DataJjtldaQuery {
    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String seriesName;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String documentName;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String fileNumber;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String fileTime;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String keywords;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String person;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String locationName;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String organization;

    @YuQueryColumn(operator = YuOperatorEnum.INNER_LIKE)
    private String notes;
}

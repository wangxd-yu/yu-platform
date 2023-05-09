package org.yu.generate.modules.view.domain;

import lombok.Data;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import org.yu.generate.enums.GenQueryComponentEnum;

/**
 * 前端 查询条件实体
 *
 * @author wangxd
 * @date 2023-04-15 23:31
 */
@Data
public class GenViewTableQueryDO {
    /**
     * 字段名
     */
    private String field;

    /**
     * 标签名
     */
    private String title;

    /**
     * 查询条件组件类型
     */
    private GenQueryComponentEnum componentEnum;

    /**
     * 查询条件 运算操作
     */
    private YuOperatorEnum operatorEnum;

}

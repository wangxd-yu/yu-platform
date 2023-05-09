package org.yu.generate.modules.view.domain;

import lombok.Data;
import org.yu.generate.enums.GenFormComponentEnum;
import org.yu.generate.enums.GenFormRuleEnum;

/**
 * @author wangxd
 * @date 2023-04-15 23:33
 */
@Data
public class GenViewFormColumnDO {
    /**
     * 字段名
     */
    private String field;

    /**
     * 标签名
     */
    private String title;

    /**
     * 组件
     */
    private GenFormComponentEnum componentEnum;

    /**
     * 组件长度
     */
    private Integer length;

    /**
     * 校验规则
     */
    private GenFormRuleEnum ruleEnum;

    /**
     * 是否必填
     */
    private Boolean required;

    /**
     * 校验提示 为空时，使用默认
     */
    private String ruleMsg;
}

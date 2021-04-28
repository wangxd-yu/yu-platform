package org.yu.common.querydsl.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;

/**
 * @author wangxd
 * @date 2020-08-19
 */
@Data
public abstract class AbstractQuery {

    @YuQueryColumn(operator = YuOperatorEnum.START_WITH)
    private String deptNo;

/*
    @YuQueryColumn(operator = YuOperatorEnum.IN, propName = "deptId")
    private Set<String> deptIds;

    public String getDeptId() {
        if (YuUtil.isDeptLevelDataScope()) {
            return SecurityUtils.getDeptId();
        } else {
            return deptId;
        }
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Set<String> getDeptIds() {
        if (YuUtil.isDeptLevelDataScope()) {
            return null;
        } else {
            return deptIds;
        }
    }

    public void setDeptIds(Set<String> deptIds) {
        this.deptIds = deptIds;
    }*/
}

package com.yu.common.querydsl.query;

import com.yu.common.querydsl.query.annotation.YuQueryColumn;
import com.yu.common.querydsl.query.enums.YuOperatorEnum;
import lombok.Data;

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
        if (SdsesUtil.isDeptLevelDataScope()) {
            return SecurityUtils.getDeptId();
        } else {
            return deptId;
        }
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Set<String> getDeptIds() {
        if (SdsesUtil.isDeptLevelDataScope()) {
            return null;
        } else {
            return deptIds;
        }
    }

    public void setDeptIds(Set<String> deptIds) {
        this.deptIds = deptIds;
    }*/
}

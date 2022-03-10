package org.yu.common.querydsl.query;

import lombok.Data;
import org.yu.common.core.util.DataScopeUtil;
import org.yu.common.core.util.SecurityUtil;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;

import java.util.Set;

/**
 * @author wangxd
 * @date 2020-08-19
 */
@Data
public abstract class AbstractQuery {

    @YuQueryColumn
    private String deptId;

    @YuQueryColumn(operator = YuOperatorEnum.IN, propName = "deptId")
    private Set<String> deptIds;

    /**
     * 只查看本级，默认false，按用户数据权限查看
     */
    private Boolean isCurrent = false;

    public void handlerDataScope() {
        if(deptId == null) {
            deptId = SecurityUtil.getDeptId();
        }
        if(isCurrent) {
            deptIds = null;
        } else {
            if (deptIds == null) {
                deptIds = DataScopeUtil.listCurrentAndSonDeptIds(deptId);
            }
            deptId = null;
        }
    }

/*    public String getDeptId() {
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

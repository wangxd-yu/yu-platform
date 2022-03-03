package org.yu.serve.system.module.dept.service.impl;

import com.querydsl.jpa.JPAExpressions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.dept.domain.*;
import org.yu.serve.system.module.dept.repository.DeptTypeRepository;
import org.yu.serve.system.module.dept.service.DeptService;
import org.yu.serve.system.module.dept.service.DeptTypeService;

import java.util.Collections;
import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-30 14:46
 */
@Service
public class DeptTypeServiceImpl extends DslBaseServiceImpl<DeptTypeRepository, DeptTypeDO, String> implements DeptTypeService {

    private final QDeptTypeDO qDeptTypeDO = QDeptTypeDO.deptTypeDO;
    private final QDeptTypeRoleDO qDeptTypeRoleDO = QDeptTypeRoleDO.deptTypeRoleDO;

    private final DeptService deptService;

    public DeptTypeServiceImpl(DeptService deptService) {
        this.deptService = deptService;
    }

    @Override
    public List<DeptTypeDO> findSubTypesByTypeId(String typeId) {
        return getJPAQueryFactory().selectFrom(qDeptTypeDO)
                .where(qDeptTypeDO.id.in(
                        JPAExpressions.select(qDeptTypeRoleDO.subTypeId)
                                .from(qDeptTypeRoleDO)
                                .where(qDeptTypeRoleDO.typeId.eq(typeId))
                )).fetch();
    }

    @Override
    public List<DeptTypeDO> findSubTypesByDeptId(String deptId) {
        DeptDO deptDO = deptService.getById(deptId);
        return findSubTypesByTypeId(deptDO.getTypeId());
    }

    @Override
    public List<DeptTypeDO> findTypesByDeptId(String deptId) {
        // 父节点
        QDeptDO m = new QDeptDO("m");
        // 子节点
        QDeptDO n = new QDeptDO("n");
        String pTypeId = getJPAQueryFactory()
                .select(m.typeId)
                .from(m,n)
                .where(
                        n.id.eq(deptId),
                        m.id.eq(n.pid)
                ).fetchOne();
        if(StringUtils.hasLength(pTypeId)) {
            return findSubTypesByTypeId(pTypeId);
        } else {
            return Collections.EMPTY_LIST;
        }
    }
}

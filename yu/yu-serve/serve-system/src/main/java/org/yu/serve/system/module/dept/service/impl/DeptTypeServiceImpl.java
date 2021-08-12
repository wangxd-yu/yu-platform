package org.yu.serve.system.module.dept.service.impl;

import com.querydsl.jpa.JPAExpressions;
import org.springframework.stereotype.Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.dept.domain.DeptTypeDO;
import org.yu.serve.system.module.dept.domain.QDeptTypeDO;
import org.yu.serve.system.module.dept.domain.QDeptTypeRoleDO;
import org.yu.serve.system.module.dept.repository.DeptTypeRepository;
import org.yu.serve.system.module.dept.service.DeptService;
import org.yu.serve.system.module.dept.service.DeptTypeService;

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
    public List<DeptTypeDO> findSubTypesByDeptNo(String deptNo) {
        DeptDO deptDO = deptService.getByNo(deptNo);
        return findSubTypesByTypeId(deptDO.getTypeId());
    }
}

package org.yu.serve.system.module.dept.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.exception.BadRequestException;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.dept.domain.QDeptDO;
import org.yu.serve.system.module.dept.dto.DeptTreeDTO;
import org.yu.serve.system.module.dept.query.DeptQuery;
import org.yu.serve.system.module.dept.repository.DeptRepository;
import org.yu.serve.system.module.dept.service.DeptService;
import org.yu.serve.system.module.dept.service.DeptTreeService;

import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-30 14:59
 */
@Service
public class DeptServiceImpl extends DslBaseServiceImpl<DeptRepository, DeptDO, String> implements DeptService {
    private final DeptTreeService deptTreeService;
    private final QDeptDO qDeptDO = QDeptDO.deptDO;
    /**
     * 顶级部门 pno 默认值
     */
    private final String ROOT_PNO = "0";

    public DeptServiceImpl(DeptTreeService deptTreeService) {
        this.deptTreeService = deptTreeService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeptDO save(DeptDO deptDO) {
        //TODO 合法校验
        if (StringUtils.isEmpty(deptDO.getPno())) {
            deptDO.setPno(ROOT_PNO);
        } else {
            if (this.getByNo(deptDO.getPno()) == null) {
                throw new BadRequestException("上级部门编号错误！");
            }
        }
        deptDO.setNo(this.genNo(deptDO.getPno()));
        deptDO.setEnabled(true);
        deptDO.setSubCount(0);
        deptDO.setTenantId(YuContextHolder.getYuContext().getTenantId());
        baseRepository.save(deptDO);
        this.updateSubCnt(deptDO.getPno());
        deptTreeService.initTree();
        return deptDO;
    }

    /**
     * 生成 上下级关系编码 NO
     */
    private String genNo(String pno) {
        //1、查询pno = pno的子部门的最大值
        String maxSubNo;
        JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
        maxSubNo = jpaQueryFactory.select(qDeptDO.no.max())
                .from(qDeptDO)
                .where(qDeptDO.pno.eq(pno))
                .fetchOne();
        if (maxSubNo == null) {
            maxSubNo = pno + "000";
        }

        int deptNum = Integer.parseInt(maxSubNo.substring(maxSubNo.length() - 3));
        if (deptNum >= 999) {
            throw new BadRequestException("不能在当前父机构下创建子机构，机构编号已用完，请联系管理员！");
        }
        maxSubNo = pno + String.format("%03d", deptNum + 1);
        return maxSubNo;
    }

    private void updateSubCnt(String deptNo) {
        if (deptNo != null) {
            JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
            int count = baseRepository.countByPno(deptNo);
            jpaQueryFactory.update(qDeptDO)
                    .set(qDeptDO.subCount, count)
                    .where(qDeptDO.no.eq(deptNo))
                    .execute();
        }
    }

    @Override
    public DeptDO getByNo(String deptNo) {
        return getJPAQueryFactory().selectFrom(qDeptDO).where(qDeptDO.no.eq(deptNo)).fetchOne();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T extends DeptTreeDTO> List<T> queryAll(DeptQuery query, Class clazz) {
        return super.queryDTO(query, null, clazz).getData();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeEnabled(String id, boolean enabled) {
        getJPAQueryFactory().update(qDeptDO)
                .set(qDeptDO.enabled, enabled)
                .where(qDeptDO.id.eq(id))
                .execute();
    }
}

package org.yu.serve.system.module.dept.service.impl;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.exception.ServiceException;
import org.yu.common.core.service.DataScopeService;
import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.query.util.WrapDataUtil;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.dept.domain.DeptRoleDO;
import org.yu.serve.system.module.dept.domain.QDeptDO;
import org.yu.serve.system.module.dept.domain.QDeptRoleDO;
import org.yu.serve.system.module.dept.dto.DeptRoleDTO;
import org.yu.serve.system.module.dept.dto.DeptTreeDTO;
import org.yu.serve.system.module.dept.query.DeptQuery;
import org.yu.serve.system.module.dept.repository.DeptRepository;
import org.yu.serve.system.module.dept.repository.DeptRoleRepository;
import org.yu.serve.system.module.dept.service.DeptService;
import org.yu.serve.system.module.dept.service.DeptTreeService;
import org.yu.serve.system.module.role.domain.QRoleDO;

import java.util.*;

/**
 * @author wangxd
 * @date 2020-11-30 14:59
 */
@Slf4j
@Service
public class DeptServiceImpl extends DslBaseServiceImpl<DeptRepository, DeptDO, String> implements DeptService, DataScopeService {
    private final DeptTreeService deptTreeService;
    private final QDeptDO qDeptDO = QDeptDO.deptDO;
    private final QRoleDO qRoleDO = QRoleDO.roleDO;
    private final QDeptRoleDO qDeptRoleDO = QDeptRoleDO.deptRoleDO;

    /**
     * 顶级部门 pid 默认值
     */
    private final String ROOT_PNO = "0";

    private final DeptRoleRepository deptRoleRepository;

    public DeptServiceImpl(DeptTreeService deptTreeService, DeptRoleRepository deptRoleRepository) {
        this.deptTreeService = deptTreeService;
        this.deptRoleRepository = deptRoleRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeptDO save(DeptDO deptDO) {
        //TODO 合法校验
        if (!StringUtils.hasLength(deptDO.getPid())) {
            deptDO.setPid(ROOT_PNO);
        } else {
            if (this.getById(deptDO.getPid()) == null) {
                throw new ServiceException("上级部门编号错误！");
            }
        }
        // deptDO.setId(this.genNo(deptDO.getPid()));
        deptDO.setEnabled(true);
        deptDO.setSubCount(0);
        deptDO.setTenantId(YuContextHolder.getYuContext().getTenantId());

        DeptDO pDeptDO = baseRepository.getById(deptDO.getPid());
        deptDO.setFullName(pDeptDO.getFullName() + "/" + deptDO);

        deptDO.setFullName(populateFullName(deptDO));

        baseRepository.save(deptDO);
        this.updateSubCnt(deptDO.getPid());
        deptTreeService.initTree();
        return deptDO;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DeptDO domain) {
        DeptDO rawDeptDO = baseRepository.getById(domain.getId());
        // 部门 名称修改
        String fullName = null;
        if (!rawDeptDO.getName().equals(domain.getName())) {
            if (rawDeptDO.getFullName().indexOf("/") > -1) {
                fullName = rawDeptDO.getFullName().substring(0, rawDeptDO.getFullName().lastIndexOf("/")) + domain.getName();
            } else {
                fullName = domain.getName();
            }
        }

        super.update(domain);
        //更新子节点 + 当前节点 的 fullName
        if (fullName != null) {
            getJPAQueryFactory().update(qDeptDO)
                    .set(qDeptDO.fullName, Expressions.stringTemplate("replace({0},{1},{2})", qDeptDO.fullName, rawDeptDO.getFullName(), fullName))
                    .where(qDeptDO.fullName.startsWith(rawDeptDO.getFullName()))
                    .execute();
        }

        log.info(String.valueOf(listSonDeptIds(domain.getId())));
    }

    /**
     * 生成 fullName
     *
     * @param deptDO
     */
    private String populateFullName(DeptDO deptDO) {
        //1、获取 父节点 (此处默认存在父节点)
        DeptDO pDeptDO = baseRepository.getById(deptDO.getPid());
        //2、父节点 + 当前节点 部门名称 = 当前节点 fullName
        return pDeptDO.getFullName() + "/" + deptDO.getName();
    }

    /**
     * 生成 上下级关系编码 NO
     */
    private String genNo(String pid) {
        //1、查询pid = pid的子部门的最大值
        String maxSubNo;
        JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
        maxSubNo = jpaQueryFactory.select(qDeptDO.id.max())
                .from(qDeptDO)
                .where(qDeptDO.pid.eq(pid))
                .fetchOne();
        if (maxSubNo == null) {
            maxSubNo = pid + "000";
        }

        int deptNum = Integer.parseInt(maxSubNo.substring(maxSubNo.length() - 3));
        if (deptNum >= 999) {
            throw new ServiceException("不能在当前父机构下创建子机构，机构编号已用完，请联系管理员！");
        }
        maxSubNo = pid + String.format("%03d", deptNum + 1);
        return maxSubNo;
    }

    private void updateSubCnt(String deptId) {
        if (deptId != null) {
            JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
            int count = baseRepository.countByPid(deptId);
            jpaQueryFactory.update(qDeptDO)
                    .set(qDeptDO.subCount, count)
                    .where(qDeptDO.id.eq(deptId))
                    .execute();
        }
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

    @Override
    public MultiDataResult<DeptRoleDTO> getDeptRoles(String deptId) {
        return WrapDataUtil.toList(getJPAQueryFactory()
                .select(YuQueryHelp.getJpaDTOSelect(DeptRoleDTO.class))
                .from(qDeptRoleDO, qRoleDO)
                .where(
                        qDeptRoleDO.deptId.eq(deptId),
                        qDeptRoleDO.roleId.eq(qRoleDO.id)
                ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDeptRoles(String deptId, String[] roleIds) {
        List<DeptRoleDO> deptRoleDOS = new ArrayList<>(roleIds.length);
        for (String roleId : roleIds) {
            deptRoleDOS.add(DeptRoleDO.builder()
                    .roleId(roleId)
                    .deptId(deptId)
                    .build());

        }
        deptRoleRepository.saveAll(deptRoleDOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeptRoles(String deptId, String[] roleIds) {
        getJPAQueryFactory().delete(qDeptRoleDO)
                .where(
                        qDeptRoleDO.deptId.eq(deptId),
                        qDeptRoleDO.roleId.in(roleIds)
                ).execute();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void moveIn(String deptId, String[] sourceIds) {
        // TODO deptType 类型检查
        getJPAQueryFactory().update(qDeptDO)
                .set(qDeptDO.pid, deptId)
                .where(qDeptDO.id.in(sourceIds))
                .execute();

        // 更新子节点数量字段
        updateSubCnt(deptId);

        // 更新子节点 fullName
        DeptDO fatherDeptDO = baseRepository.getById(deptId);
        List<DeptDO> deptDOS = baseRepository.findAllById(Arrays.asList(sourceIds));
        deptDOS.forEach(item -> {
            String fullName = (fatherDeptDO.getFullName() != null ? fatherDeptDO.getFullName() + "/" : "") + item.getName();
            getJPAQueryFactory().update(qDeptDO)
                    .set(qDeptDO.fullName, Expressions.stringTemplate("replace({0},{1},{2})", qDeptDO.fullName, item.getFullName(), fullName))
                    .where(qDeptDO.fullName.startsWith(item.getFullName()))
                    .execute();
        });
    }

    @Override
    public Set<String> listSonDeptIds(String deptId) {
        //TODO 放redis缓存
        Map<String, Set<String>> setMap = getJPAQueryFactory()
                .from(qDeptDO)
                .transform(GroupBy.groupBy(qDeptDO.pid).as(GroupBy.set(qDeptDO.id)));

        return recursionSonDeptIds(deptId, setMap);
    }

    Set<String> recursionSonDeptIds(String deptId, Map<String, Set<String>> setMap) {
        Set<String> sonDeptIds = new HashSet();
        if (setMap.get(deptId) != null) {
            sonDeptIds.addAll(setMap.get(deptId));
            for (String pid : setMap.get(deptId)) {
                if (setMap.containsKey(pid)) {
                    sonDeptIds.addAll(recursionSonDeptIds(pid, setMap));
                }
            }
        }
        return sonDeptIds;
    }

    @Override
    public Set<String> listParentDeptIds(String deptId) {
        return null;
    }
}

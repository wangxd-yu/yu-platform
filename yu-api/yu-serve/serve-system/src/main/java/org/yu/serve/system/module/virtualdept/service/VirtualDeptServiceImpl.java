package org.yu.serve.system.module.virtualdept.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.dept.dto.DeptTreeDTO;
import org.yu.serve.system.module.virtualdept.domain.QVirtualDeptDO;
import org.yu.serve.system.module.virtualdept.domain.QVirtualDeptDeptDO;
import org.yu.serve.system.module.virtualdept.domain.VirtualDeptDO;
import org.yu.serve.system.module.virtualdept.domain.VirtualDeptDeptDO;
import org.yu.serve.system.module.virtualdept.query.VirtualDeptQuery;
import org.yu.serve.system.module.virtualdept.repository.VirtualDeptDeptRepository;
import org.yu.serve.system.module.virtualdept.repository.VirtualDeptRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangxd
 * @date 2022-04-01
 */
@Service
public class VirtualDeptServiceImpl extends DslBaseServiceImpl<VirtualDeptRepository, VirtualDeptDO, String> implements VirtualDeptService {

    private final QVirtualDeptDO qVirtualDeptDO = QVirtualDeptDO.virtualDeptDO;
    private final QVirtualDeptDeptDO qVirtualDeptDeptDO = QVirtualDeptDeptDO.virtualDeptDeptDO;

    private final VirtualDeptDeptRepository virtualDeptDeptRepository;

    public VirtualDeptServiceImpl(VirtualDeptDeptRepository virtualDeptDeptRepository) {
        this.virtualDeptDeptRepository = virtualDeptDeptRepository;
    }

    @Override
    public VirtualDeptDO save(VirtualDeptDO domain) {
        domain.setEnabled(true);
        domain.setSubCount(0);
        return super.save(domain);
    }

    @Override
    public <T extends DeptTreeDTO> List<T> queryAll(VirtualDeptQuery query, Class clazz) {
        return null;
    }

    @Override
    public List<String> getMenuEndpoints(String virtualDeptId) {
        return getJPAQueryFactory().select(qVirtualDeptDeptDO.deptId)
                .from(qVirtualDeptDO, qVirtualDeptDeptDO)
                .where(
                        qVirtualDeptDO.id.eq(virtualDeptId),
                        qVirtualDeptDeptDO.virtualDeptId.eq(qVirtualDeptDO.id)
                ).fetch();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object saveVirtualDeptDeptIds(String id, String[] deptIds) {
        //1、先删 原有数据
        getJPAQueryFactory().delete(qVirtualDeptDeptDO).where(qVirtualDeptDeptDO.virtualDeptId.eq(id)).execute();
        //2、新增
        List<VirtualDeptDeptDO> virtualDeptDeptDOS = Arrays.stream(deptIds).map(deptId -> new VirtualDeptDeptDO(id, deptId)).collect(Collectors.toList());
        return virtualDeptDeptRepository.saveAll(virtualDeptDeptDOS);
    }
}

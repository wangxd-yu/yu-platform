package org.yu.serve.system.module.dept.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.serve.system.module.dept.domain.QDeptDO;
import org.yu.serve.system.module.dept.dto.DeptTreeDTO;
import org.yu.serve.system.module.dept.service.DeptTreeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangxd
 * @date 2020-11-30 19:48
 */
@Service
public class DeptTreeServiceImpl implements DeptTreeService {

    @PersistenceContext
    private EntityManager entityManager;
    QDeptDO qDeptDO = QDeptDO.deptDO;

    //每个级别部门 数量量级
    private static final int PER_LEVEL_DEPT_DIGIT = 3;

    //存放包含子节点的dept，方便快速查询树节点
    private Map<String, DeptTreeDTO> deptMapCache = new HashMap<>();

    @Override
    //@PostConstruct
    public void initTree() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        List<DeptTreeDTO> deptDTOS = jpaQueryFactory.select(
                YuQueryHelp.getJpaDTOSelect(DeptTreeDTO.class)
        ).from(qDeptDO).fetch();
        buildTreeInit(deptDTOS);
    }

    public void buildTreeInit(List<DeptTreeDTO> deptDTOS) {
        buildTree(deptDTOS, true);
    }

    @Override
    public <T extends DeptTreeDTO> Map<String, Object> buildTree(List<T> deptDTOS) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("total", deptDTOS.size());
        map.put("data", buildShowTree(deptDTOS));
        return map;
    }

    @Override
    public <T extends DeptTreeDTO> List<T> buildShowTree(List<T> deptDTOS) {
        return buildTree(deptDTOS, false);
    }

    @Override
    public List<DeptTreeDTO> getShowTreeByDeptId(String deptId) {
        DeptTreeDTO deptTreeDTO = deptMapCache.get(deptId);
        if (deptTreeDTO == null) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(deptMapCache.get(deptId));
        }
    }

    private <T extends DeptTreeDTO> List<T> buildTree(List<T> deptDTOS, boolean cacheFlag) {
        Map<String, List<T>> zoneByParentIdMap = deptDTOS.stream().collect(Collectors.groupingBy(DeptTreeDTO::getPid));
        deptDTOS.forEach(zone-> {
            List<T> children = zoneByParentIdMap.get(zone.getId());
            zone.setChildren(zoneByParentIdMap.get(zone.getId()));
            if(children != null) {
                children.forEach(item -> item.setHasParent(true));
            }
        });
        return deptDTOS.stream().filter(v -> !v.isHasParent()).collect(Collectors.toList());
    }

    /**
     * 树结构泛型类
     */
    private <T extends DeptTreeDTO> List<T> buildTree_bak(List<T> deptDTOS, boolean cacheFlag) {
        // TODO 多租户处理
        deptMapCache = new HashMap<>();
        //加缓存
        Map<String, T> parentDeptMap = new HashMap<>();
        deptDTOS.forEach(dept -> {
            //根节点 直接放入缓存
            if (dept.getId().equals("0")) {
                if (cacheFlag) {
                    parentDeptMap.put(dept.getId(), dept);
                }
            } else {
                //查找父节点,先从 map中找，再从列表中查询
                T pDeptDto = parentDeptMap.get(dept.getId());
                if (pDeptDto != null) {
                    pDeptDto.getChildren().add(dept);
                    dept.setHasParent(true);
                } else {
                    //查找 dept 的上级部门
                    pDeptDto = deptDTOS.parallelStream()
                            .filter(deptDTO -> dept.getPid().equals(deptDTO.getId()))
                            .findAny().orElse(null);
                    if (pDeptDto != null) {
                        if (pDeptDto.getChildren() == null) {
                            pDeptDto.setChildren(new ArrayList<>());
                            parentDeptMap.put(pDeptDto.getId(), pDeptDto);
                            if (cacheFlag) {
                                deptMapCache.put(pDeptDto.getId(), pDeptDto);
                            }
                        }
                        pDeptDto.getChildren().add(dept);
                        dept.setHasParent(true);
                    }
                }
            }
        });
        deptDTOS.removeIf(T::isHasParent);
        return deptDTOS;
    }
}

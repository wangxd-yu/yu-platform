package org.yu.serve.system.module.dept.service.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.serve.system.module.dept.domain.QDeptDO;
import org.yu.serve.system.module.dept.dto.DeptTreeDTO;
import org.yu.serve.system.module.dept.service.DeptTreeService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

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
    public List<DeptTreeDTO> getShowTreeByDeptNo(String deptNo) {
        DeptTreeDTO deptTreeDTO = deptMapCache.get(deptNo);
        if (deptTreeDTO == null) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(deptMapCache.get(deptNo));
        }
    }

    /**
     * 树结构泛型类
     */
    private <T extends DeptTreeDTO> List<T> buildTree(List<T> deptDTOS, boolean cacheFlag) {
        // TODO 多租户处理
        deptMapCache = new HashMap<>();
        //加缓存
        Map<String, T> parentDeptMap = new HashMap<>();
        deptDTOS.forEach(dept -> {
            //根节点 直接放入缓存
            if (dept.getNo().length() == PER_LEVEL_DEPT_DIGIT) {
                if (cacheFlag) {
                    parentDeptMap.put(dept.getNo(), dept);
                }
            } else {
                //查找父节点,先从 map中找，再从列表中查询
                T pDeptDto = parentDeptMap.get(dept.getNo());
                if (pDeptDto != null) {
                    pDeptDto.getChildren().add(dept);
                    dept.setHasParent(true);
                } else {
                    //查找 dept 的上级部门
                    pDeptDto = deptDTOS.parallelStream().filter(deptDTO ->
                            dept.getNo().substring(0, dept.getNo().length() - PER_LEVEL_DEPT_DIGIT).equals(deptDTO.getNo())
                    ).findAny().orElse(null);
                    if (pDeptDto != null) {
                        if (pDeptDto.getChildren() == null) {
                            pDeptDto.setChildren(new ArrayList<>());
                            parentDeptMap.put(pDeptDto.getNo(), pDeptDto);
                            if (cacheFlag) {
                                deptMapCache.put(pDeptDto.getNo(), pDeptDto);
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

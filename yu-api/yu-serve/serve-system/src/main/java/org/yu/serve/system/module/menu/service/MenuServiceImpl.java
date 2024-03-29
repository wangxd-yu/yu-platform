package org.yu.serve.system.module.menu.service;

import cn.hutool.core.util.StrUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.serve.system.module.endpoint.domain.EndpointDO;
import org.yu.serve.system.module.endpoint.domain.QEndpointDO;
import org.yu.serve.system.module.menu.domain.MenuDO;
import org.yu.serve.system.module.menu.domain.MenuEndpointDO;
import org.yu.serve.system.module.menu.domain.QMenuDO;
import org.yu.serve.system.module.menu.domain.QMenuEndpointDO;
import org.yu.serve.system.module.menu.dto.MenuBuildDTO;
import org.yu.serve.system.module.menu.dto.MenuDTO;
import org.yu.serve.system.module.menu.eumus.MenuTypeEnum;
import org.yu.serve.system.module.menu.repository.MenuEndpointRepository;
import org.yu.serve.system.module.menu.repository.MenuRepository;
import org.yu.serve.system.module.menu.vo.MenuMetaVO;
import org.yu.serve.system.module.menu.vo.MenuVO;
import org.yu.serve.system.module.role.domain.QRoleDO;
import org.yu.serve.system.module.role.domain.QRoleMenuDO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangxd
 * @date 2020-11-26 14:50
 */
@Service
public class MenuServiceImpl extends DslBaseServiceImpl<MenuRepository, MenuDO, String> implements MenuService {
    private final QMenuDO qMenuDO = QMenuDO.menuDO;
    private final QRoleDO qRoleDO = QRoleDO.roleDO;
    private final QMenuEndpointDO qMenuEndpointDO = QMenuEndpointDO.menuEndpointDO;
    private final QEndpointDO qEndpointDO = QEndpointDO.endpointDO;

    private final MenuEndpointRepository menuEndpointRepository;

    public MenuServiceImpl(MenuEndpointRepository menuEndpointRepository) {
        this.menuEndpointRepository = menuEndpointRepository;
    }

    @Override
    public List<MenuBuildDTO> findByRoleCodes(Set<String> roleCodes) {
        JPAQueryFactory jpaQueryFactory = super.getJPAQueryFactory();
        QRoleMenuDO qRoleMenuDO = QRoleMenuDO.roleMenuDO;

        return jpaQueryFactory.select(YuQueryHelp.getJpaDTOSelect(MenuBuildDTO.class))
                .from(qMenuDO)
                .leftJoin(qRoleMenuDO).on(qRoleMenuDO.menuId.eq(qMenuDO.id))
                .leftJoin(qRoleDO).on(qRoleMenuDO.roleId.eq(qRoleDO.id))
                .where(
                        qRoleDO.code.in(roleCodes),
                        qMenuDO.type.ne(MenuTypeEnum.PERMISSION)
                ).orderBy(qMenuDO.sort.asc())
                .distinct().fetch();
    }

    @Override
    public Map<String, Object> buildTree(List<MenuDTO> menuDTOS) {
        /*List<MenuDTO> trees = new ArrayList<>();
        for (MenuDTO menuDTO : menuDTOS) {
            if ("0".equals(menuDTO.getPid())) {
                trees.add(menuDTO);
            }
            for (MenuDTO it : menuDTOS) {
                if (it.getPid().equals(menuDTO.getId())) {
                    if (menuDTO.getChildren() == null) {
                        menuDTO.setChildren(new ArrayList<>());
                    }
                    menuDTO.getChildren().add(it);
                }
            }
        }*/
        Map<String, List<MenuDTO>> zoneByParentIdMap = menuDTOS.stream().collect(Collectors.groupingBy(MenuDTO::getPid));
        menuDTOS.forEach(zone-> {
            // List<MenuDTO> children = zoneByParentIdMap.get(zone.getId());
            zone.setChildren(zoneByParentIdMap.get(zone.getId()));
        });
        List<MenuDTO> trees = menuDTOS.stream().filter(v -> Objects.equals("0", v.getPid())).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("data", trees.size() == 0 ? menuDTOS : trees);
        map.put("total", menuDTOS.size());
        map.put("success", "true");
        return map;
    }

    @Override
    public List<MenuVO> buildMenus(List<MenuDTO> menuDTOS) {
        List<MenuVO> list = new LinkedList<>();
        // 如果不是外链
        menuDTOS.stream().filter(Objects::nonNull).forEach(menuDTO -> {
            List<MenuDTO> menuDTOList = menuDTO.getChildren();
            MenuVO menuVo = new MenuVO();
            menuVo.setIcon(menuDTO.getIcon());
            menuVo.setName(menuDTO.getName());
            menuVo.setPath(menuDTO.getPath());
            if (!menuDTO.getFrame()) {
                if (menuDTO.getPid().equals(0L)) {
                    // 一级目录需要加斜杠，不然访问 会跳转404页面
                    menuVo.setPath("/" + menuDTO.getPath());
                    menuVo.setComponent(StrUtil.isEmpty(menuDTO.getComponent()) ? "Layout" : menuDTO.getComponent());
                } else if (!StrUtil.isEmpty(menuDTO.getComponent())) {
                    menuVo.setComponent(menuDTO.getComponent());
                }
            }
            menuVo.setMeta(new MenuMetaVO(menuDTO.getName(), menuDTO.getIcon()));
            if (menuDTOList != null && menuDTOList.size() != 0) {
                menuVo.setAlwaysShow(true);
                menuVo.setRedirect("noredirect");
                menuVo.setChildren(buildMenus(menuDTOList));
                // 处理是一级菜单并且没有子菜单的情况
            } else if (menuDTO.getPid().equals(0L)) {
                MenuVO menuVo1 = new MenuVO();
                menuVo1.setMeta(menuVo.getMeta());
                // 非外链
                if (!menuDTO.getFrame()) {
                    menuVo1.setPath("index");
                    menuVo1.setName(menuVo.getName());
                    menuVo1.setComponent(menuVo.getComponent());
                } else {
                    menuVo1.setPath(menuDTO.getPath());
                }
                menuVo.setName(null);
                menuVo.setMeta(null);
                menuVo.setComponent("Layout");
                List<MenuVO> list1 = new ArrayList<>();
                list1.add(menuVo1);
                menuVo.setChildren(list1);
            }
            list.add(menuVo);
        });
        return list;
    }

    @Override
    public Object getMenuTree(List<MenuDO> menus) {
        List<Map<String, Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
            if (menu != null) {
                List<MenuDO> menuList = this.baseRepository.findByPid(menu.getId());
                Map<String, Object> map = new HashMap<>();
                map.put("id", menu.getId());
                map.put("label", menu.getName());
                if (menuList != null && menuList.size() != 0) {
                    map.put("children", getMenuTree(menuList));
                }
                list.add(map);
            }
        });
        return list;
    }

    @Override
    public List<MenuDO> findByPid(String pid) {
        return baseRepository.findByPid(pid);
    }

    @Override
    public Object getMenuEndpoints(String id, Pageable pageable) {
        JPAQuery<EndpointDO> jpaQuery = getJPAQueryFactory()
                .select(qEndpointDO)
                .from(qEndpointDO, qMenuEndpointDO)
                .where(
                        qMenuEndpointDO.menuId.eq(id),
                        qEndpointDO.id.eq(qMenuEndpointDO.endpointId)
                );
        return getMultiDataResult(jpaQuery, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Object saveMenuEndpoints(String id, String[] endpointsIds) {
        List<MenuEndpointDO> menuEndpointDOS = Arrays.stream(endpointsIds).map(endpointsId -> new MenuEndpointDO(id, endpointsId)).collect(Collectors.toList());
        return menuEndpointRepository.saveAll(menuEndpointDOS);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteMenuEndpoint(String id, String endpointId) {
        getJPAQueryFactory().delete(qMenuEndpointDO).where(
                qMenuEndpointDO.menuId.eq(id),
                qMenuEndpointDO.endpointId.eq(endpointId)
        ).execute();
    }
}

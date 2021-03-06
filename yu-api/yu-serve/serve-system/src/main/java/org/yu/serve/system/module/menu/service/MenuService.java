package org.yu.serve.system.module.menu.service;

import org.springframework.data.domain.Pageable;
import org.yu.common.querydsl.service.DslBaseService;
import org.yu.serve.system.module.menu.domain.MenuDO;
import org.yu.serve.system.module.menu.dto.MenuBuildDTO;
import org.yu.serve.system.module.menu.dto.MenuDTO;
import org.yu.serve.system.module.menu.vo.MenuVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangxd
 * @date 2020-11-26 14:45
 */
public interface MenuService extends DslBaseService<MenuDO, String> {

    /**
     * 根据角色code列表获取菜单
     *
     * @param roleCodes 角色code列表
     */
    List<MenuBuildDTO> findByRoleCodes(Set<String> roleCodes);

    Map<String, Object> buildTree(List<MenuDTO> menuDTOS);

    /**
     * 构建前端菜单树
     */
    List<MenuVO> buildMenus(List<MenuDTO> menuDTOS);

    Object getMenuTree(List<MenuDO> menus);

    List<MenuDO> findByPid(String pid);

    Object getMenuEndpoints(String id, Pageable pageable);

    Object saveMenuEndpoints(String id, String[] endpointsIds);

    void deleteMenuEndpoint(String id, String endpointId);
}

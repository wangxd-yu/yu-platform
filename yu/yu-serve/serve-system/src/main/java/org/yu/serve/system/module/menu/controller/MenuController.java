package org.yu.serve.system.module.menu.controller;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.menu.domain.MenuDO;
import org.yu.serve.system.module.menu.dto.MenuDTO;
import org.yu.serve.system.module.menu.query.MenuQuery;
import org.yu.serve.system.module.menu.service.MenuService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author wangxd
 * @date 2020-11-26 14:44
 */
@RestController
@RequestMapping("menu")
public class MenuController extends DslBaseApiController<MenuService, MenuDO, Long> {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        super(menuService);
        this.menuService = menuService;
    }

    @GetMapping("build")
    public ResponseEntity<Object> buildMenus() {
        List<String> roleCodes = YuContextHolder.getYuContext().getClientUser().getRoles();
        if (CollectionUtil.isEmpty(roleCodes)) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        List<MenuDTO> menus = menuService.findByRoleCodes(roleCodes);
        List<MenuDTO> menuDTOTree = (List<MenuDTO>) menuService.buildTree(menus).get("content");
        return new ResponseEntity<>(menuService.buildMenus(menuDTOTree), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Map<?, ?>> getMenus(MenuQuery criteria) {
        MultiDataResult<MenuDTO> menuDTOS = menuService.queryDTO(criteria, null, MenuDTO.class);
        return new ResponseEntity<>(menuService.buildTree(menuDTOS.getData()), HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     */
    @GetMapping("tree")
    public ResponseEntity<Object> getMenuTree() {
        return new ResponseEntity<>(menuService.getMenuTree(menuService.findByPid(0L)), HttpStatus.OK);
    }
}

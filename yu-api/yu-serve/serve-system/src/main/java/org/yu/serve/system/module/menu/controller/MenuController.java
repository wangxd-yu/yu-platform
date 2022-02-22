package org.yu.serve.system.module.menu.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.common.querydsl.util.TreeUtil;
import org.yu.serve.system.module.menu.domain.MenuDO;
import org.yu.serve.system.module.menu.dto.MenuBuildDTO;
import org.yu.serve.system.module.menu.dto.MenuDTO;
import org.yu.serve.system.module.menu.query.MenuQuery;
import org.yu.serve.system.module.menu.service.MenuService;
import org.yu.serve.system.module.user.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wangxd
 * @date 2020-11-26 14:44
 */
@RestController
@RequestMapping("menu")
public class MenuController extends DslBaseApiController<MenuService, MenuDO, String> {

    private final MenuService menuService;
    private final UserService userService;

    public MenuController(MenuService menuService, UserService userService) {
        super(menuService);
        this.menuService = menuService;
        this.userService = userService;
    }

    @GetMapping("build")
    public ResponseEntity<Object> buildMenus() {
        Set<String> roleCodes = userService.getRoleCodesByUserId(YuContextHolder.getYuContext().getSecurityUser().getId());
        if (CollectionUtil.isEmpty(roleCodes)) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }
        List<MenuBuildDTO> menus = menuService.findByRoleCodes(roleCodes);
        return new ResponseEntity<>(TreeUtil.buildTree(menus), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getMenus(MenuQuery criteria) {
        return new ResponseEntity<>(menuService.queryDTO(criteria, null, MenuDTO.class), HttpStatus.OK);
    }

    /**
     * 返回全部的菜单
     */
    @GetMapping("tree")
    public ResponseEntity<Object> getMenuTree() {
        return new ResponseEntity<>(menuService.getMenuTree(menuService.findByPid("0")), HttpStatus.OK);
    }

    @GetMapping("{id}/endpoints")
    public ResponseEntity<Object> getMenuEndpoints(@PathVariable String id, Pageable pageable) {
        return new ResponseEntity<>(menuService.getMenuEndpoints(id, pageable), HttpStatus.OK);
    }

    @PostMapping("{id}/endpoints")
    public ResponseEntity<Object> saveMenuEndpoints(@PathVariable String id, @RequestBody Map<String, List<String>> map) {
        return new ResponseEntity<>(menuService.saveMenuEndpoints(id, Convert.toStrArray(map.get("endpointIds"))), HttpStatus.CREATED);
    }

    @DeleteMapping("{id}/endpoints/{endpointId}")
    public ResponseEntity<Object> deleteMenuEndpoint(@PathVariable String id, @PathVariable String endpointId) {
        menuService.deleteMenuEndpoint(id, endpointId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

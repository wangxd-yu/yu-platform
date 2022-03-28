package org.yu.serve.system.module.dept.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yu.common.core.util.DataScopeUtil;
import org.yu.common.core.util.SecurityUtil;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.dept.dto.DeptDTO;
import org.yu.serve.system.module.dept.query.DeptQuery;
import org.yu.serve.system.module.dept.service.DeptService;
import org.yu.serve.system.module.dept.service.DeptTreeService;

import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-30 17:19
 */
@RestController
@RequestMapping("dept")
public class DeptController extends DslBaseApiController<DeptService, DeptDO, String> {

    private final DeptTreeService deptTreeService;

    protected DeptController(DeptService deptService, DeptTreeService deptTreeService) {
        super(deptService);
        this.deptTreeService = deptTreeService;
    }

    @GetMapping
    public ResponseEntity<Object> getPages(DeptQuery query, Pageable pageable) {
        /*if (query.getNo() == null) {
            query.setNo(YuContextHolder.getYuContext().getSecurityUser().getDeptId());
        }*/
        return new ResponseEntity<>(dslBaseService.queryDTO(query, pageable, DeptDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/tree")
    public ResponseEntity<Object> getDeptTree(DeptQuery query) {
        query.setIds(DataScopeUtil.listCurrentAndSonDeptIds(SecurityUtil.getDeptId()));
        List<DeptDTO> deptDTOList = dslBaseService.queryAll(query, DeptDTO.class);
        return new ResponseEntity<>(deptTreeService.buildTree(deptDTOList), HttpStatus.OK);
        //return new ResponseEntity<>(deptTreeService.getShowTreeByDeptId(YuContextHolder.getYuContext().getSecurityUser().getDeptId()), HttpStatus.OK);
    }

    @PutMapping("{id}:moveIn")
    public ResponseEntity<Object> moveIn(@PathVariable String id, @RequestBody String[] sourceIds) {
        this.dslBaseService.moveIn(id, sourceIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}/enable")
    public ResponseEntity<Object> enable(@PathVariable String id) {
        this.dslBaseService.changeEnabled(id, true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}/disable")
    public ResponseEntity<Object> disable(@PathVariable String id) {
        this.dslBaseService.changeEnabled(id, false);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "{id}/roles")
    public ResponseEntity<Object> getDeptRoles(@PathVariable String id) {
        return new ResponseEntity<>(dslBaseService.getDeptRoles(id), HttpStatus.OK);
    }

    @PostMapping(value = "{id}/roles")
    public ResponseEntity<Object> saveDeptRoles(@PathVariable("id") String deptId, @RequestBody String[] roleIds) {
        dslBaseService.saveDeptRoles(deptId, roleIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "{id}/roles:batchDelete")
    public ResponseEntity<Object> deleteDeptRoles(@PathVariable("id") String deptId, @RequestBody String[] roleIds) {
        dslBaseService.deleteDeptRoles(deptId, roleIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

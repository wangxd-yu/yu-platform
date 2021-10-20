package org.yu.serve.system.module.dept.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yu.common.core.context.YuContextHolder;
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
    public ResponseEntity<Object> getPages(DeptQuery query) {
        if (query.getNo() == null) {
            query.setNo(YuContextHolder.getYuContext().getClientUser().getDeptNo());
        }
        List<DeptDTO> deptDTOList = dslBaseService.queryAll(query, DeptDTO.class);
        return new ResponseEntity<>(deptTreeService.buildTree(deptDTOList), HttpStatus.OK);
    }

    @GetMapping(value = "/tree")
    public ResponseEntity<Object> getDeptTree() {
        return new ResponseEntity<>(deptTreeService.getShowTreeByDeptNo(YuContextHolder.getYuContext().getClientUser().getDeptNo()), HttpStatus.OK);
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
}

package org.yu.serve.system.module.dept.controller;

import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.dept.dto.DeptDTO;
import org.yu.serve.system.module.dept.query.DeptQuery;
import org.yu.serve.system.module.dept.service.DeptService;
import org.yu.serve.system.module.dept.service.DeptTreeService;
import org.yu.common.core.context.YuContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-30 17:19
 */
@RestController
@RequestMapping("dept")
public class DeptController extends DslBaseApiController<DeptService, DeptDO, Long> {

    private final DeptTreeService deptTreeService;

    protected DeptController(DeptService deptService, DeptTreeService deptTreeService) {
        super(deptService);
        this.deptTreeService = deptTreeService;
    }

    @GetMapping
    public ResponseEntity<Object> getPages(DeptQuery query, Pageable pageable) {
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
}
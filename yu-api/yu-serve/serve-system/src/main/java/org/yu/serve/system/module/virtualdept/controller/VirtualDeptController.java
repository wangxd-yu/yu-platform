package org.yu.serve.system.module.virtualdept.controller;

import cn.hutool.core.convert.Convert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.controller.DslBaseController;
import org.yu.common.querydsl.util.TreeUtil;
import org.yu.serve.system.module.virtualdept.domain.VirtualDeptDO;
import org.yu.serve.system.module.virtualdept.dto.VirtualDeptDTO;
import org.yu.serve.system.module.virtualdept.query.VirtualDeptQuery;
import org.yu.serve.system.module.virtualdept.service.VirtualDeptService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author wangxd
 */
@Validated
@RestController
@RequestMapping("virtualDept")
public class VirtualDeptController extends DslBaseController<VirtualDeptService, VirtualDeptDO, String> {

    protected VirtualDeptController(VirtualDeptService dslBaseService) {
        super(dslBaseService);
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid VirtualDeptDO domain) throws Exception {
        return super.save(domain);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return super.getById(id);
    }

    @GetMapping(value = "tree")
    public ResponseEntity<Object> getDeptTree(VirtualDeptQuery query) {
        List<VirtualDeptDTO> virtualDeptDTOList = dslBaseService.queryList(query, VirtualDeptDTO.class);
        return new ResponseEntity<>(MultiDataResult.<VirtualDeptDTO>builder()
                .data(TreeUtil.buildTree(virtualDeptDTOList))
                .total((long) virtualDeptDTOList.size()).build(), HttpStatus.OK);
    }

    @GetMapping("{id}/deptIds")
    public ResponseEntity<Object> getRelationDeptIds(@PathVariable String id) {
        return new ResponseEntity<>(dslBaseService.getMenuEndpoints(id), HttpStatus.OK);
    }

    @PostMapping("{id}/deptIds")
    public ResponseEntity<Object> saveMenuEndpoints(@PathVariable String id, @RequestBody Map<String, List<String>> map) {
        return new ResponseEntity<>(dslBaseService.saveVirtualDeptDeptIds(id, Convert.toStrArray(map.get("deptIds"))), HttpStatus.CREATED);
    }
}

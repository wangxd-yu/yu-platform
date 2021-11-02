package org.yu.serve.system.module.dict.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.common.querydsl.controller.DslBaseController;
import org.yu.common.querydsl.valid.annotation.YuDependValid;
import org.yu.serve.system.module.dict.domain.DictDO;
import org.yu.serve.system.module.dict.domain.DictDetailDO;
import org.yu.serve.system.module.dict.query.DictQuery;
import org.yu.serve.system.module.dict.service.DictService;

import javax.validation.Valid;

/**
 * @author wangxd
 * @date 2020-11-27 14:35
 */
@Validated
@RestController
@RequestMapping("dict")
public class DictController extends DslBaseController<DictService, DictDO, String> {
    protected DictController(DictService dictService) {
        super(dictService);
    }

    @GetMapping
    public ResponseEntity<Object> getPage(DictQuery query, Pageable pageable) {
        return super.query(query, pageable);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return super.getById(id);
    }

    @PostMapping
    public ResponseEntity<Object> saveValid(@RequestBody @Valid DictDO domain) throws Exception {
        return super.save(domain);
    }

    @PutMapping
    public ResponseEntity<Object> updateValid(@RequestBody @Valid DictDO domain) {
        return super.update(domain);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteValid(
            @YuDependValid(domain = DictDetailDO.class, prop = "dictId", exist = false)
            @PathVariable String id) {
        return super.delete(id);
    }
}

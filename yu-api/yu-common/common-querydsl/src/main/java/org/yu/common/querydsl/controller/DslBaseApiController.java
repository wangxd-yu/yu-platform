package org.yu.common.querydsl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yu.common.querydsl.domain.DslBaseDO;
import org.yu.common.querydsl.service.DslBaseService;

import javax.validation.Valid;

/**
 * @author wangxd
 * @date 2020-11-25
 */
public abstract class DslBaseApiController<M extends DslBaseService<DO, ID>, DO extends DslBaseDO<?>, ID>
        extends DslBaseController<M, DO, ID> {

    protected final M dslBaseService;

    protected DslBaseApiController(M dslBaseService) {
        super(dslBaseService);
        this.dslBaseService = dslBaseService;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable ID id) {
        return super.getById(id);
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid DO domain) throws Exception {
        return super.save(domain);
    }

    @Override
    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Valid DO domain) {
        return super.update(domain);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable ID id) {
        return super.delete(id);
    }
}
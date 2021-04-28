package org.yu.common.querydsl.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.yu.common.querydsl.domain.DslBaseDO;
import org.yu.common.querydsl.service.DslBaseService;

/**
 * @author wangxd
 * @date 2020-11-25
 */
public abstract class DslBaseController<M extends DslBaseService<DO, ID>, DO extends DslBaseDO<?>, ID> {

    protected final M dslBaseService;

    protected DslBaseController(M dslBaseService) {
        this.dslBaseService = dslBaseService;
    }

    public ResponseEntity<Object> getById(@PathVariable ID id) {
        return new ResponseEntity<>(dslBaseService.getById(id), HttpStatus.OK);
    }

    public ResponseEntity<Object> save(@RequestBody DO domain) throws Exception {
        if (domain.getId() != null) {
            throw new Exception("A new " + domain.getClass().getSimpleName() + " cannot already have an ID");
        }
        return new ResponseEntity<>(dslBaseService.save(domain), HttpStatus.CREATED);
    }

    public ResponseEntity<Object> update(@RequestBody DO domain) {
        dslBaseService.update(domain);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Object> delete(@PathVariable ID id) {
        dslBaseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected <Q> ResponseEntity<Object> query(Q query, Pageable pageable) {
        return new ResponseEntity<>(dslBaseService.query(query, pageable), HttpStatus.OK);
    }

    protected <Q, DTO> ResponseEntity<Object> queryDTO(Q query, Pageable pageable, Class<DTO> clazz) {
        return new ResponseEntity<>(dslBaseService.queryDTO(query, pageable, clazz), HttpStatus.OK);
    }
}
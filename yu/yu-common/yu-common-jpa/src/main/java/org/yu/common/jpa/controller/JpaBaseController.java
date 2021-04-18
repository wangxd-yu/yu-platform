package org.yu.common.jpa.controller;

import org.yu.common.jpa.domain.JpaBaseDO;
import org.yu.common.jpa.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangxd
 * @date 2020-11-09 10:10
 */
public abstract class JpaBaseController<M extends BaseService<DO, Long>, DO extends JpaBaseDO> {

    protected final M baseService;

    protected JpaBaseController(M baseService) {
        this.baseService = baseService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        return new ResponseEntity<>(baseService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody DO domain) throws Exception {
        if (domain.getId() != null) {
            throw new Exception("A new " + domain.getClass().getSimpleName() + " cannot already have an ID");
        }
        return new ResponseEntity<>(baseService.save(domain), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody DO domain) {
        baseService.update(domain);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        baseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

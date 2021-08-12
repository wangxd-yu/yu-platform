package org.yu.serve.system.module.dict.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.dict.domain.DictDO;
import org.yu.serve.system.module.dict.query.DictQuery;
import org.yu.serve.system.module.dict.service.DictService;

/**
 * @author wangxd
 * @date 2020-11-27 14:35
 */
@RestController
@RequestMapping("dict")
public class DictController extends DslBaseApiController<DictService, DictDO, String> {
    protected DictController(DictService dictService) {
        super(dictService);
    }

    @GetMapping
    public ResponseEntity<Object> getPage(DictQuery query, Pageable pageable) {
        return super.query(query, pageable);
    }
}

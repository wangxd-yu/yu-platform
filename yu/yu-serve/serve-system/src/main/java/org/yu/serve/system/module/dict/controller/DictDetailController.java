package org.yu.serve.system.module.dict.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.dict.domain.DictDetailDO;
import org.yu.serve.system.module.dict.dto.DictDetailMiniDTO;
import org.yu.serve.system.module.dict.query.DictDetailMiniQuery;
import org.yu.serve.system.module.dict.query.DictDetailQuery;
import org.yu.serve.system.module.dict.service.DictDetailService;

/**
 * @author wangxd
 * @date 2020-11-27 15:08
 */
@RestController
@RequestMapping("dictDetail")
public class DictDetailController extends DslBaseApiController<DictDetailService, DictDetailDO, Long> {

    protected DictDetailController(DictDetailService dictDetailService) {
        super(dictDetailService);
    }

    @GetMapping
    public ResponseEntity<Object> getPages(DictDetailQuery query, Pageable pageable) {
        return super.query(query, pageable);
    }

    @GetMapping("list")
    public ResponseEntity<Object> getList(DictDetailMiniQuery query) {
        return super.queryDTO(query, null, DictDetailMiniDTO.class);
    }
}

package org.yu.admin.base.log.contoller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.admin.base.log.domain.LogLoginDO;
import org.yu.admin.base.log.query.LogLoginQuery;
import org.yu.admin.base.log.service.LogLoginService;
import org.yu.common.querydsl.controller.DslBaseController;

/**
 * @author wangxd
 * @date 2021-11-24 23:13
 */
@RestController
@RequestMapping("logLogin")
public class LogLoginController extends DslBaseController<LogLoginService, LogLoginDO, String> {
    protected LogLoginController(LogLoginService dslBaseService) {
        super(dslBaseService);
    }

    @GetMapping
    public ResponseEntity<Object> getPage(LogLoginQuery query, Pageable pageable) {
        return super.query(query, pageable);
    }
}
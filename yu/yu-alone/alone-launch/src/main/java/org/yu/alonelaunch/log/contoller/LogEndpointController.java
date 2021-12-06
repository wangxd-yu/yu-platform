package org.yu.alonelaunch.log.contoller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.alonelaunch.log.domain.LogEndpointDO;
import org.yu.alonelaunch.log.dto.LogEndpointDTO;
import org.yu.alonelaunch.log.query.LogEndpointQuery;
import org.yu.alonelaunch.log.service.LogEndpointService;
import org.yu.common.querydsl.controller.DslBaseController;

/**
 * @author wangxd
 * @date 2021-11-24 23:13
 */
@RestController
@RequestMapping("logEndpoint")
public class LogEndpointController extends DslBaseController<LogEndpointService, LogEndpointDO, String> {
    protected LogEndpointController(LogEndpointService dslBaseService) {
        super(dslBaseService);
    }

    @GetMapping
    public ResponseEntity<Object> getPage(LogEndpointQuery query, Pageable pageable) {
        return super.queryDTO(query, pageable, LogEndpointDTO.class);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return super.getById(id);
    }
}
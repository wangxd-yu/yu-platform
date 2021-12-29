package org.yu.serve.system.module.endpoint.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.endpoint.domain.EndpointDO;
import org.yu.serve.system.module.endpoint.query.EndpointQuery;
import org.yu.serve.system.module.endpoint.service.EndpointService;

/**
 * 端点 controller
 *
 * @author wangxd
 * @date 2021-09-09
 */
@RestController
@RequestMapping("endpoints")
public class EndpointController extends DslBaseApiController<EndpointService, EndpointDO, String> {

    protected EndpointController(EndpointService endpointService) {
        super(endpointService);
    }

    @GetMapping()
    public ResponseEntity<Object> all() {
        return new ResponseEntity<>(this.dslBaseService.getRequestMappingInfos(), HttpStatus.OK);
    }

    @GetMapping(":search")
    public ResponseEntity<Object> getPage(EndpointQuery query, Pageable pageable) {
        return super.query(query, pageable);
    }

    @PutMapping("/{id}:accessEnable")
    public ResponseEntity<Object> accessEnable(@PathVariable String id) {
        this.dslBaseService.changeAccessEnabled(id, true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}:accessDisable")
    public ResponseEntity<Object> accessDisable(@PathVariable String id) {
        this.dslBaseService.changeAccessEnabled(id, false);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}:logEnable")
    public ResponseEntity<Object> logEnable(@PathVariable String id) {
        this.dslBaseService.changeLogEnabled(id, true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}:logDisable")
    public ResponseEntity<Object> logDisable(@PathVariable String id) {
        this.dslBaseService.changeLogEnabled(id, false);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

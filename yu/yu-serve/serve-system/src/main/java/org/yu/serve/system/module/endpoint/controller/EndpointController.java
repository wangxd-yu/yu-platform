package org.yu.serve.system.module.endpoint.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yu.common.querydsl.controller.DslBaseApiController;
import org.yu.serve.system.module.endpoint.domain.EndpointDO;
import org.yu.serve.system.module.endpoint.service.EndpointService;

/**
 * 端点 controller
 *
 * @author wangxd
 * @date 2021-09-09
 */
@RestController
@RequestMapping("endpoint")
public class EndpointController extends DslBaseApiController<EndpointService, EndpointDO, String> {

    protected EndpointController(EndpointService endpointService) {
        super(endpointService);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> all() {
        return new ResponseEntity<>(this.dslBaseService.getRequestMappingInfos(), HttpStatus.OK);
    }
}

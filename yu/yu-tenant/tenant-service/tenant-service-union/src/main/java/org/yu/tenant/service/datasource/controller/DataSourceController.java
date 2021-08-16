package org.yu.tenant.service.datasource.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yu.common.querydsl.controller.DslBaseController;
import org.yu.tenant.service.api.datasource.domain.DataSourceDO;
import org.yu.tenant.service.api.datasource.query.DataSourceQuery;
import org.yu.tenant.service.datasource.service.DataSourceService;

/**
 * DataSourceApiController
 * DataSourceFeign 调用
 *
 * @author wangxd
 * @date 2021-04-06 09:12
 */
@RestController
@RequestMapping("datasources")
public class DataSourceController extends DslBaseController<DataSourceService, DataSourceDO, Long> {
    private final DataSourceService datasourceService;

    public DataSourceController(DataSourceService datasourceService) {
        super(datasourceService);
        this.datasourceService = datasourceService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDataSource(@PathVariable Long id) {
        return super.getById(id);
    }

    @GetMapping
    public ResponseEntity<Object> listDataSources(DataSourceQuery datasourceQuery, Pageable pageable) {
        return super.query(datasourceQuery, pageable);
    }

    @PostMapping("/{id}/enabled")
    public ResponseEntity<Object> enabled(@PathVariable Long id) {
        datasourceService.enableDataSource(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/disabled")
    public ResponseEntity<Object> disabled(@PathVariable Long id) {
        datasourceService.disableDataSource(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

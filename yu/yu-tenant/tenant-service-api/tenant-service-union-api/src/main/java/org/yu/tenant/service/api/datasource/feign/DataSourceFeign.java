package org.yu.tenant.service.api.datasource.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 数据源-feign
 *
 * @author wangxd
 * @date 2021-03-25 17:25
 */
@FeignClient(name = "tenant-service")
public interface DataSourceFeign {
    String API_PREFIX = "/datasources";

    /**
     * 查询数据源
     *
     * @param id 主键
     * @return 数据源DO
     */
    @GetMapping(API_PREFIX + "/{id}")
    ResponseEntity<Object> getDataSource(@PathVariable("id") Long id);

    /**
     * 查询数据源列表-根据组id
     *
     * @param groupId 组id
     * @return 数据源列表
     */
    @GetMapping(API_PREFIX)
    ResponseEntity<Object> listDataSources(@RequestParam("groupId") Long groupId,
                                           @RequestParam(name = "unPaged") boolean unPaged);
}

package org.yu.admin.archive.module.intro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yu.admin.archive.module.intro.domain.IntroDO;
import org.yu.admin.archive.module.intro.domain.IntroSnapshotDO;
import org.yu.admin.archive.module.intro.service.IntroService;
import org.yu.common.querydsl.controller.DslBaseController;

/**
 * @author wangxd
 * @date 2024-12-21 13:36
 */
@RestController
@RequestMapping("intro")
public class IntroController extends DslBaseController<IntroService, IntroDO, String> {

    private final IntroService introService;

    protected IntroController(IntroService dslBaseService, IntroService introService) {
        super(dslBaseService);
        this.introService = introService;
    }

    @GetMapping("{databaseId}")
    public ResponseEntity<Object> getIntroByDatabaseId(@PathVariable String databaseId) {
        return new ResponseEntity<>(introService.findByDatabaseId(databaseId), HttpStatus.OK);
    }

    @DeleteMapping("delete/{databaseId}")
    public ResponseEntity<Object> deleteByDatabaseId(@PathVariable String databaseId) {
        introService.deleteByDatabaseId(databaseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("list/{databaseId}")
    public ResponseEntity<Object> findListByDatabaseId(@PathVariable String databaseId) {
        return new ResponseEntity<>(introService.findListByDatabaseId(databaseId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> saveOrUpdate(@RequestBody IntroDO domain) {
        introService.saveOrUpdate(domain);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("snapshot/{databaseId}")
    public ResponseEntity<Object> getSnapshotIntroByDatabaseId(@PathVariable String databaseId) {
        return new ResponseEntity<>(introService.findSnapshotByDatabaseId(databaseId), HttpStatus.OK);
    }

    @PostMapping("snapshot")
    public ResponseEntity<Object> saveOrUpdateSnapshot(@RequestBody IntroSnapshotDO introSnapshotDO) {
        introService.saveOrUpdate(introSnapshotDO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

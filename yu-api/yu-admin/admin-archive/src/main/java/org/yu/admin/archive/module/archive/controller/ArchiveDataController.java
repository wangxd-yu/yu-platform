package org.yu.admin.archive.module.archive.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yu.admin.archive.module.archive.service.ArchiveDataService;

import java.util.List;
import java.util.Map;

/**
 * @author wangxd
 * @date 2024-12-22 13:57
 */
@RestController
@RequestMapping("archive")
public class ArchiveDataController {

    private final ArchiveDataService archiveDataService;

    public ArchiveDataController(ArchiveDataService archiveDataService) {
        this.archiveDataService = archiveDataService;
    }

    /**
     * 分页查询
     *
     * @param databaseType
     * @param queryParams
     * @param pageable
     * @return
     */
    @GetMapping("{databaseType}/page")
    public ResponseEntity<Object> getPage(@PathVariable String databaseType, @RequestParam Map<String, String> queryParams, Pageable pageable) {
        return new ResponseEntity<>(archiveDataService.query(databaseType, queryParams, pageable), HttpStatus.OK);
    }

    @GetMapping("{databaseType}/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable String databaseType, @PathVariable String id) {
        return new ResponseEntity<>(archiveDataService.getById(databaseType, id), HttpStatus.OK);
    }

    @PostMapping("{databaseType}/uploadZip")
    public ResponseEntity<Object> uploadZip(@PathVariable String databaseType, MultipartFile file) {
        archiveDataService.uploadZip(databaseType, file);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{databaseType}/create")
    public ResponseEntity<Object> create(@PathVariable String databaseType,
                                         @RequestParam String domain,
                                         @RequestParam("wordFile") MultipartFile wordFile,
                                         @RequestParam("pdfFiles") List<MultipartFile> pdfFiles) {
        archiveDataService.create(databaseType, domain, wordFile, pdfFiles);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{databaseType}/update")
    public ResponseEntity<Object> updateData(@PathVariable String databaseType, @RequestBody Object domain) {
        archiveDataService.updateData(databaseType, domain);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{databaseType}/updateHtml")
    public ResponseEntity<Object> updateHtml(@PathVariable String databaseType, @RequestBody Map<String, String> map) {
        archiveDataService.updateHtml(databaseType, map.get("id"), map.get("html"));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("{databaseType}/delete/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable String databaseType, @PathVariable String id) {
        archiveDataService.deleteById(databaseType, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

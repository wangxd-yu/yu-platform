package org.yu.generate.modules.template.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yu.common.querydsl.controller.DslBaseController;
import org.yu.generate.modules.template.domain.GenCodeTemplateDO;
import org.yu.generate.modules.template.dto.CatalogUpdateDTO;
import org.yu.generate.modules.template.dto.TemplateCodeDTO;
import org.yu.generate.modules.template.query.GenCodeTemplateQuery;
import org.yu.generate.modules.template.service.GenCodeTemplateService;

/**
 * @author wangxd
 * @date 2024-02-22 19:53
 */
@Validated
@RestController
@RequestMapping("genCodeTemplate")
public class GenCodeTemplateController extends DslBaseController<GenCodeTemplateService, GenCodeTemplateDO, String> {

    protected GenCodeTemplateController(GenCodeTemplateService dslBaseService) {
        super(dslBaseService);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable String id) {
        return super.getById(id);
    }

    @GetMapping
    public ResponseEntity<Object> getPage(GenCodeTemplateQuery query, Pageable pageable) {
        return super.query(query, pageable);
    }

    @PutMapping("updateCatalog/{id}")
    public ResponseEntity<Object> updateCatalog(@PathVariable String id, @RequestBody CatalogUpdateDTO catalogUpdateDTO) {
        this.dslBaseService.updateCatalog(id, catalogUpdateDTO.getCatalog());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("templateCode")
    public ResponseEntity<Object> save(@RequestBody TemplateCodeDTO domain) {
        return new ResponseEntity<>(this.dslBaseService.saveTemplateCode(domain), HttpStatus.CREATED);
    }

    @GetMapping("templateCode/{templateId}/{codeKey}")
    public ResponseEntity<Object> getByTemplateAndKey(@PathVariable String templateId, @PathVariable String codeKey) {
        return new ResponseEntity<>(this.dslBaseService.getByTemplateAndKey(templateId, codeKey), HttpStatus.OK);
    }


}

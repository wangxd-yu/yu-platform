package ${genApiModuleDO.packagePath}.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yu.common.querydsl.controller.DslBaseController;
import org.yu.common.web.valid.annotation.YuDependValid;
import ${genApiModuleDO.packagePath}.domain.${genApiModuleDO.genApiDomainDO.upperDomainName}DO;
import ${genApiModuleDO.packagePath}.query.${genApiModuleDO.genApiDomainDO.upperDomainName}Query;
import ${genApiModuleDO.packagePath}.service.${genApiModuleDO.genApiDomainDO.upperDomainName}Service;

import javax.validation.Valid;

/**
* @author ${genApiModuleDO.author}
* @date ${date}
*/
@Validated
@RestController
@RequestMapping("${genApiModuleDO.genApiDomainDO.lowerDomainName}")
public class ${genApiModuleDO.genApiDomainDO.upperDomainName}Controller extends DslBaseController
<${genApiModuleDO.genApiDomainDO.upperDomainName}Service, ${genApiModuleDO.genApiDomainDO.upperDomainName}DO, String>; {
protected ${genApiModuleDO.genApiDomainDO.upperDomainName}Controller(${genApiModuleDO.genApiDomainDO.upperDomainName}Service ${genApiModuleDO.genApiDomainDO.lowerDomainName}Service) {
super(${genApiModuleDO.genApiDomainDO.lowerDomainName}Service);
}

@GetMapping
public ResponseEntity
<Object> getPage(${genApiModuleDO.genApiDomainDO.upperDomainName}Query query, Pageable pageable) {
    return super.query(query, pageable);
    }

    @Override
    @GetMapping("{id}")
    public ResponseEntity
    <Object> getById(@PathVariable String id) {
        return super.getById(id);
        }

        @PostMapping
        public ResponseEntity
        <Object> saveValid(@RequestBody @Valid ${genApiModuleDO.genApiDomainDO.upperDomainName}DO domain) throws
            Exception {
            return super.save(domain);
            }

            @PutMapping
            public ResponseEntity
            <Object> updateValid(@RequestBody @Valid ${genApiModuleDO.genApiDomainDO.upperDomainName}DO domain) {
                return super.update(domain);
                }

                @DeleteMapping("{id}")
                public ResponseEntity
                <Object> deleteValid(@PathVariable String id) {
                    return super.delete(id);
                    }
                    }
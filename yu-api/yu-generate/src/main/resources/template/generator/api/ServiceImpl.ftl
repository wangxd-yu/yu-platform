package ${genApiModuleDO.packagePath}.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ${genApiModuleDO.packagePath}.domain.${genApiModuleDO.genApiDomainDO.upperDomainName}DO;
import ${genApiModuleDO.packagePath}.repository.${genApiModuleDO.genApiDomainDO.upperDomainName}Repository;
import ${genApiModuleDO.packagePath}.service.${genApiModuleDO.genApiDomainDO.upperDomainName}Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;

/**
* @author ${genApiModuleDO.author}
* @date ${date}
*/
@Slf4j
@Service
public class ${genApiModuleDO.genApiDomainDO.upperDomainName}ServiceImpl extends DslBaseServiceImpl
<${genApiModuleDO.genApiDomainDO.upperDomainName}Repository, ${genApiModuleDO.genApiDomainDO.upperDomainName}DO, String> implements ${genApiModuleDO.genApiDomainDO.upperDomainName}Service {

}

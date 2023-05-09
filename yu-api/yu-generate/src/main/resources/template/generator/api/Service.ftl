package ${genApiModuleDO.packagePath}.service;

import org.yu.common.querydsl.service.DslBaseService;
import ${genApiModuleDO.packagePath}.domain.${genApiModuleDO.genApiDomainDO.upperDomainName}DO;

/**
* @author ${genApiModuleDO.author}
* @date ${date}
*/
public interface ${genApiModuleDO.genApiDomainDO.upperDomainName}Service extends DslBaseService
<${genApiModuleDO.genApiDomainDO.upperDomainName}DO, String> {
}

package ${genApiModuleDO.packagePath}.repository;

import ${genApiModuleDO.packagePath}.domain.${genApiModuleDO.genApiDomainDO.upperDomainName}DO;
import org.yu.common.querydsl.repository.DslBaseRepository;

/**
* @author ${genApiModuleDO.author}
* @date ${date}
*/
public interface ${genApiModuleDO.genApiDomainDO.upperDomainName}Repository extends DslBaseRepository
<${genApiModuleDO.genApiDomainDO.upperDomainName}DO, String> {
}

export type ${module.genViewModuleDO.moduleName?cap_first} = {
<#if module.genApiModuleDO.genApiDomainDO.domainFields??>
  <#list module.genApiModuleDO.genApiDomainDO.domainFields as field>
    ${field.name}?: string;
  </#list>
</#if>
};

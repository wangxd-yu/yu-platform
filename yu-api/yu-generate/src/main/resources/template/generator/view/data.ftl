export type ${genViewModuleDO.genViewTable.tableName}Data = {
<#if genViewModuleDO.genViewTable.tableColumns??>
  <#list genViewModuleDO.genViewTable.tableColumns as tableColumn>
    ${tableColumn.dataIndex}?: string;
  </#list>
</#if>
};

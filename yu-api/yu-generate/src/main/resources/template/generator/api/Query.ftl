<#-- 在这个版本中，将 underscoreToCamel 的逻辑内联 -->
<#assign tableDO = module.genViewModuleDO.genViewCrudDO.genViewTableDO>
package ${genApiModuleDO.packagePath}.query;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuOrderColumn;
import org.yu.common.querydsl.query.annotation.YuQuery;
import org.yu.common.querydsl.query.annotation.YuQueryColumn;
import org.yu.common.querydsl.query.enums.YuOperatorEnum;
import ${genApiModuleDO.packagePath}.domain.${genApiModuleDO.genApiDomainDO.upperDomainName}DO;

/**
* @author ${genApiModuleDO.author}
* @date ${date}
*/
@Data
@YuQuery(
domain = ${genApiModuleDO.genApiDomainDO.upperDomainName}DO.class
)
public class ${genApiModuleDO.genApiDomainDO.upperDomainName}Query {
<#if tableDO.columns??>
    <#list tableDO.columns as column>
        <#if column.search??>
            @YuQueryColumn(operator = YuOperatorEnum.${column.operator!})
            private String ${column.field};

        </#if>
    </#list>
</#if>
}

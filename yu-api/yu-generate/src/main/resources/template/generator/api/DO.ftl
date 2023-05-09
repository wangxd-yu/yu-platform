package ${genApiModuleDO.packagePath}.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.yu.common.querydsl.domain.DslBaseDeptDO;
<#if importTypes??>

    <#list importTypes as importType>
        import ${importType};
    </#list>

</#if>
import javax.persistence.Entity;
import javax.persistence.Table;

/**
* @author ${genApiModuleDO.author}
* @date ${date}
*/
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "${genApiModuleDO.genApiDomainDO.tableName}")
public class ${genApiModuleDO.genApiDomainDO.upperDomainName}DO extends DslBaseDeptDO
<String> {
    <#if genApiModuleDO.genApiDomainDO.domainFields??>
        <#list genApiModuleDO.genApiDomainDO.domainFields as domainField>
            <#if domainField.columnComment??>
                /**
                * ${domainField.columnComment}
                */
            </#if>
            private ${domainField.javaType} ${domainField.lowerColumnName};

        </#list>
    </#if>
    }
package ${targetPackage};

import lombok.Data;

<#if importClassList?exists>
    <#list importClassList?keys as mykey>
import ${mykey};
    </#list>
</#if>
import java.io.Serializable;

<#include "header.ftl">
public @Data class ${domainObjectClassName} implements Serializable {

<#if columns?exists>
    <#list columns as field>
    /** ${field['columnComment']} */
    private ${field['javaType']} ${field['columnName']};
    </#list>
</#if>


}

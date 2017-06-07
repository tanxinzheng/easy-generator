package ${targetPackage};

import lombok.Data;
import com.xmomen.framework.model.BaseEntity;

<#if importClassList?exists>
    <#list importClassList?keys as mykey>
import ${mykey};
    </#list>
</#if>
import java.io.Serializable;

<#include "header.ftl">
public @Data class ${domainObjectClassName} extends BaseEntity implements Serializable {

<#if columns?exists>
    <#list columns as field>
    /** ${field['columnComment']} */
    private ${field['javaType']} ${field['columnName']};
    </#list>
</#if>


}

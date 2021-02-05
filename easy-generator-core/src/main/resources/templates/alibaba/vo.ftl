package ${targetPackage};

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

<#if importClassList?exists>
    <#list importClassList?keys as mykey>
import ${mykey};
    </#list>
</#if>
import java.io.Serializable;

<#include "header.ftl"/>
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "${tableComment}")
public class ${domainObjectClassName}VO implements Serializable {

<#if columns?exists>
    <#list columns as field>
    @ApiModelProperty(value = "${field['columnComment']}")
    private ${field['javaType']} ${field['columnName']};
    </#list>
</#if>


}

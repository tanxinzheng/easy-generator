package ${targetPackage};

import com.github.tanxinzheng.framework.model.BaseModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if supportExcel >
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;
</#if>

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
public class ${domainObjectClassName}DTO extends BaseModel implements Serializable {
<#if columns?exists>
    <#list columns as field>
    /** ${field['columnComment']} */
    <#if !field.nullable && !field.primaryKey>
    <#if field['javaType'] = 'String'>
    @NotBlank(message = "${field['columnComment']}为必填项")
    <#else>
    @NotNull(message = "${field['columnComment']}为必填项")
    </#if>
    </#if>
    <#if field['javaType'] = 'String'>
    @Length(max = ${field.length}, message = "${field['columnComment']}字符长度限制[0,${field.length}]")
    </#if>
    <#if field['javaType'] = 'Integer'>
    @Range(max = 10000000, min = 0, message = "${field['columnComment']}数值范围[10000000,0]")
    </#if>
    <#if field['javaType'] = 'Long'>
    @Range(max = ${field['max']}l, min = ${field['min']}l, message = "${field['columnComment']}数值范围[${field['min']}l,${field['max']}l]")
    </#if>
    <#if field['javaType'] = 'BigDecimal'>
    @DecimalMax(value = "${field['max']}", message = "${field['columnComment']}数值范围[${field['min']},${field['max']}]")
    @DecimalMin(value = "${field['min']}", message = "${field['columnComment']}数值范围[${field['min']},${field['max']}]")
    </#if>
    @ApiModelProperty(value = "${field['columnComment']}")
    private ${field['javaType']} ${field['columnName']};
    </#list>
</#if>

}

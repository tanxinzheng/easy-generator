package ${modulePackage}.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
<#if supportExcel >
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;
</#if>
import org.springframework.beans.BeanUtils;

<#if importClassList?exists>
    <#list importClassList?keys as mykey>
import ${mykey};
    </#list>
</#if>
import java.io.Serializable;


<#if supportExcel >
@ExcelTarget(value = "${domainObjectClassName}Model")
</#if>
@ApiModel(value = "${tableComment}")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ${domainObjectClassName}Model implements Serializable {

<#if columns?exists>
    <#list columns as field>
    /** ${field['columnComment']} */
    <#if !field.primaryKey && !field.hide && supportExcel>
    @Excel(name = "${field['columnComment']}")
    </#if>
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

    /**
    * Get ${domainObjectClassName} Entity Object
    * @return
    */
    @JsonIgnore
    public ${domainObjectClassName} getEntity(){
        ${domainObjectClassName} ${domainObjectName} = new ${domainObjectClassName}();
        BeanUtils.copyProperties(this, ${domainObjectName});
        return ${domainObjectName};
    }


}

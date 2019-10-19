package ${targetPackage};

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

<#if importClassList?exists>
    <#list importClassList?keys as mykey>
import ${mykey};
    </#list>
</#if>
import java.io.Serializable;

@Data
@TableName(value = "${tableName}")
public class ${domainObjectClassName} implements Serializable {

<#if columns?exists>
    <#list columns as field>
    /** ${field['columnComment']} */
    <#if field.primaryKey>
    @TableId(value = "${field.actualColumnName}", type = IdType.UUID)
    <#else>
    @TableField(value = "${field.actualColumnName}")
    </#if>
    private ${field['javaType']} ${field['columnName']};
    </#list>
</#if>


}

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${targetPackage}.${domainObjectClassName}Mapper" >

    <sql id="Base_Columns">
        <#list columns as column>
        ${column.actualColumnName}<#if column_has_next>,</#if>
        </#list>
    </sql>

    <sql id="Base_Properties">
        <#list columns as column>
        ${'#'}{${column.columnName}, jdbcType=${column.jdbcType}}<#if column_has_next>,</#if>
        </#list>
    </sql>

</mapper>
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

    <sql id="Select_By_Query_Where_Clause">
        <where>
            <if test="keyword">
                AND ${primaryKeyColumn.actualColumnName} LIKE CONCAT('%', ${r"#{keyword}"}, '%')
            </if>
            <if test="id">
                AND ${primaryKeyColumn.actualColumnName} = ${r"#{id}"}
            </if>
            <if test="ids">
                AND ${primaryKeyColumn.actualColumnName} IN
                <foreach collection="ids" item="item" separator="," open="(" close=")">
                ${r"#{item}"}
                </foreach>
            </if>
            <if test="excludeIds">
                AND ${primaryKeyColumn.actualColumnName} NOT IN
                <foreach collection="excludeIds" item="item" separator="," open="(" close=")">
                ${r"#{item}"}
                </foreach>
            </if>
        </where>
    </sql>


    <insert id="insertBatch" parameterType="java.util.List" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO ${tableName}
        (
        <include refid="Base_Columns" />
        )
        VALUES
        <foreach collection="list" item="item" open="(" separator="," close=")">
            <include refid="Base_Properties" />
        </foreach>
    </insert>

    <select id="selectModel"
            resultType="${modulePackage}.model.${domainObjectClassName}Model"
            parameterType="${modulePackage}.model.${domainObjectClassName}Query">
        SELECT
            <include refid="Base_Columns"/>
        FROM ${tableName}
        <include refid="Select_By_Query_Where_Clause"/>
        ORDER BY id
    </select>

</mapper>
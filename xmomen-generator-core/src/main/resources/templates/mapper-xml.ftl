<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${targetPackage}.${domainObjectClassName}Mapper" >

    <insert id="insertSelective" parameterType="${modulePackage}.model.${domainObjectClassName}" useGeneratedKeys="true" keyProperty="id" keyColumn="ID" >
        <selectKey resultType="java.lang.String" keyProperty="id" order="BEFORE" >
            SELECT replace(UUID(),'-','')
        </selectKey>
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides="," >
            <#list columns as field>
            <if test="${field.columnName} != null" >
                ${field.actualColumnName},
            </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides="," >
            <#list columns as field>
            <if test="${field.columnName} != null" >
            ${'#'}{${field.actualColumnName},jdbcType=${field.jdbcType}},
            </if>
            </#list>
        </trim>
    </insert>

    <delete id="deleteByPrimaryKey" parameterType="java.lang.String" >
        delete from ${tableName} where id = ${r"#{id}"}
    </delete>

    <delete id="deleteByPrimaryKey" parameterType="java.lang.String[]" >
        delete from ${tableName} where ID IN
        <foreach collection="ids" item="item" separator="," open="(" close=")">
        ${r"#{item}"}
        </foreach>
    </delete>

    <update id="updateSelective" parameterType="${modulePackage}.model.${domainObjectClassName}" >
        update ${tableName}
        <set>
        <#list columns as field>
            <if test="${field.columnName} != null" >
            ${field.actualColumnName} = ${'#'}{${field.columnName},jdbcType=${field.jdbcType}},
            </if>
        </#list>
        </set>
        WHERE ${primaryKeyColumn.actualColumnName} = ${'#'}{${primaryKeyColumn.columnName},jdbcType=${primaryKeyColumn.jdbcType}},
    </update>

    <update id="updateSelectiveByQuery" parameterType="map" >
        update ${tableName}
        <set >
        <#list columns as field>
            <if test="record.${field.columnName} != null" >
            ${field.actualColumnName} = ${'#'}{${field.actualColumnName},jdbcType=${field.jdbcType}},
            </if>
        </#list>
        </set>
        <if test="query != null" >
            <include refid="Update_By_Query_Where_Clause"/>
        </if>
    </update>

    <!--    查询消息    -->
    <select id="select"
            resultType="${modulePackage}.model.${domainObjectClassName}"
            parameterType="${modulePackage}.model.${domainObjectClassName}Query">
        SELECT * FROM ${tableName}
        <include refid="Update_By_Query_Where_Clause"/>
        ORDER BY id
    </select>

    <select id="selectByPrimaryKey"
            resultType="${modulePackage}.model.${domainObjectClassName}"
            parameterType="java.lang.String">
        SELECT * FROM ${tableName} WHERE ${primaryKeyColumn.actualColumnName} = ${'#'}{${primaryKeyColumn.columnName},jdbcType=${primaryKeyColumn.jdbcType}},
    </select>

    <select id="selectModelByPrimaryKey"
            resultType="${modulePackage}.model.${domainObjectClassName}Model"
            parameterType="java.lang.String">
        SELECT * FROM ${tableName} WHERE ${primaryKeyColumn.actualColumnName} = ${'#'}{${primaryKeyColumn.columnName},jdbcType=${primaryKeyColumn.jdbcType}},
    </select>

    <select id="selectModel"
            resultType="${modulePackage}.model.${domainObjectClassName}Model"
            parameterType="${modulePackage}.model.${domainObjectClassName}Query">
        SELECT * FROM ${tableName}
            <include refid="Update_By_Query_Where_Clause"/>
        ORDER BY id
    </select>

    <sql id="Update_By_Query_Where_Clause">
        <where>
            <if test="keyword">
                AND ID LIKE CONCAT('%', ${r"#{keyword}"}, '%')
            </if>
            <if test="id">
                AND ID = ${r"#{id}"}
            </if>
            <if test="ids">
                AND ID IN
                <foreach collection="ids" item="item" separator="," open="(" close=")">
                ${r"#{item}"}
                </foreach>
            </if>
            <if test="excludeIds">
                AND ID NOT IN
                <foreach collection="excludeIds" item="item" separator="," open="(" close=")">
                ${r"#{item}"}
                </foreach>
            </if>
        </where>
    </sql>

</mapper>
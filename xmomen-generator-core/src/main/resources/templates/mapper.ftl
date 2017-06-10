package ${targetPackage};

import ${modulePackage}.model.${domainObjectClassName};
import ${modulePackage}.model.${domainObjectClassName}Model;
import ${modulePackage}.model.${domainObjectClassName}Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

<#include "header.ftl">
public interface ${domainObjectClassName}Mapper {

    List<${domainObjectClassName}> select(${domainObjectClassName}Query ${domainObjectName}Query);

    List<${domainObjectClassName}Model> selectModel(${domainObjectClassName}Query ${domainObjectName}Query);

    ${domainObjectClassName} selectByPrimaryKey(String primaryKey);

    ${domainObjectClassName}Model selectModelByPrimaryKey(String primaryKey);

    int deleteByPrimaryKey(String primaryKey);

    int deletesByPrimaryKey(@Param("ids") List<String> primaryKeys);

    int insertSelective(${domainObjectClassName} record);

    int updateSelective(${domainObjectClassName} record);

    int updateSelectiveByQuery(@Param("record") ${domainObjectClassName} record, @Param("query") ${domainObjectClassName}Query example);
}

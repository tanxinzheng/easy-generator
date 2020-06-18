package ${targetPackage};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${modulePackage}.domain.entity.${domainObjectClassName}DO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

<#include "header.ftl"/>
@Mapper
public interface ${domainObjectClassName}Mapper extends BaseMapper<${domainObjectClassName}DO> {

}
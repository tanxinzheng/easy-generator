package ${targetPackage};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import ${modulePackage}.domain.dto.${domainObjectClassName}DTO;
import ${modulePackage}.domain.entity.${domainObjectClassName}DO;

import java.util.List;

<#include "header.ftl"/>
public interface ${domainObjectClassName}Service extends IService<${domainObjectClassName}DO> {

    /**
     * 新增${tableComment}
     * @param  ${domainObjectName}Create
     * @return ${domainObjectClassName}DTO
     */
    public ${domainObjectClassName}DTO create${domainObjectClassName}(${domainObjectClassName}DTO ${domainObjectName}Create);

    /**
     * 批量新增${tableComment}
     * @param ${domainObjectName}
     * @return List<${domainObjectClassName}>
     */
    List<${domainObjectClassName}DTO> create${domainObjectClassName}s(List<${domainObjectClassName}DTO> ${domainObjectName});

    /**
     * 更新${tableComment}
     * @param   ${domainObjectName}Update
     * @return  boolean
     */
    public boolean update${domainObjectClassName}(${domainObjectClassName}DTO ${domainObjectName}Update);

    /**
     * 主键查询对象
     * @param   id
     * @return  ${domainObjectClassName}DTO
     */
    public ${domainObjectClassName}DTO findById(String id);


    /**
    * 查询集合对象
    * @param queryWrapper
    * @return List<${domainObjectClassName}DTO>
    */
    public List<${domainObjectClassName}DTO> findList(QueryWrapper<${domainObjectClassName}DO> queryWrapper);

    /**
     * 查询${tableComment}领域分页对象
     * @param page
     * @param queryWrapper
     * @return
     */
    public IPage<${domainObjectClassName}DTO> findPage(IPage<${domainObjectClassName}DO> page, QueryWrapper<${domainObjectClassName}DO> queryWrapper);

    /**
     * 批量删除${tableComment}
     * @param  ids
     * @return boolean
     */
    public boolean delete${domainObjectClassName}(List<String> ids);

    /**
     * 删除${tableComment}
     * @param  id
     * @return boolean
     */
    public boolean delete${domainObjectClassName}(String id);

}

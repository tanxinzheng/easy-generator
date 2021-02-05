package ${targetPackage};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${modulePackage}.domain.dto.${domainObjectClassName}Request;
import ${modulePackage}.domain.dto.${domainObjectClassName}Response;
import ${modulePackage}.domain.entity.${domainObjectClassName};

import java.util.List;


public interface ${domainObjectClassName}Service extends IService<${domainObjectClassName}DO>{

    /**
     * 新增${tableComment}
     * @param ${domainObjectName}DTO
     * @return ${domainObjectClassName}DTO
     */
    public ${domainObjectClassName}DTO create${domainObjectClassName}(${domainObjectClassName} ${domainObjectName}DTO);

    /**
     * 批量新增${tableComment}
     * @param ${domainObjectName}
     * @return List<${domainObjectClassName}>
     */
    List<${domainObjectClassName}> create${domainObjectClassName}s(List<${domainObjectClassName}> ${domainObjectName});

    /**
     * 更新${tableComment}
     * @param   ${domainObjectName}DTO
     * @return  ${domainObjectClassName}DTO
     */
    public boolean update${domainObjectClassName}(${domainObjectClassName} ${domainObjectName}DTO);

    /**
     * 根据查询参数查询单个对象
     * @param   id
     * @return  ${domainObjectClassName}DTO
     */
    public ${domainObjectClassName}DTO findOne${domainObjectClassName}(String id);

    /**
     * 查询${tableComment}领域分页对象（带参数条件）
     * @param   queryParams
     * @return  Page<${domainObjectClassName}DTO>
     */
    public Page<${domainObjectClassName}DTO> findPage${domainObjectClassName}(QueryParams queryParams);

    /**
     * 批量删除${tableComment}
     * @param  ids
     * @return int
     */
    public int delete${domainObjectClassName}(List<String> ids);

    /**
     * 删除${tableComment}
     * @param  id
     * @return int
     */
    public int delete${domainObjectClassName}(String id);

}

package ${targetPackage};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${modulePackage}.domain.dto.${domainObjectClassName}Request;
import ${modulePackage}.domain.dto.${domainObjectClassName}Response;
import ${modulePackage}.domain.entity.${domainObjectClassName};

import java.util.List;


public interface ${domainObjectClassName}Service {

    /**
     * 新增${tableComment}
     * @param ${domainObjectName}Create
     * @return ${domainObjectClassName}Response
     */
    public ${domainObjectClassName}Response create${domainObjectClassName}(${domainObjectClassName} ${domainObjectName}Create);

    /**
     * 批量新增${tableComment}
     * @param ${domainObjectName}
     * @return List<${domainObjectClassName}>
     */
    List<${domainObjectClassName}> create${domainObjectClassName}s(List<${domainObjectClassName}> ${domainObjectName});

    /**
     * 更新${tableComment}
     * @param   ${domainObjectName}Update
     * @return  ${domainObjectClassName}Response
     */
    public ${domainObjectClassName}Response update${domainObjectClassName}(${domainObjectClassName} ${domainObjectName}Update);

    /**
     * 根据查询参数查询单个对象
     * @param   id
     * @return  ${domainObjectClassName}Response
     */
    public ${domainObjectClassName}Response findOne${domainObjectClassName}Response(String id);

    /**
     * 查询${tableComment}领域分页对象（带参数条件）
     * @param   ${domainObjectName}Request
     * @return  Page<${domainObjectClassName}Response>
     */
    public Page<${domainObjectClassName}Response> findPage${domainObjectClassName}Response(${domainObjectClassName}Request ${domainObjectName}Request);

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

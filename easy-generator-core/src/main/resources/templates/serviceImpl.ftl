package ${targetPackage};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${modulePackage}.domain.dto.${domainObjectClassName}Request;
import ${modulePackage}.domain.dto.${domainObjectClassName}Response;
import ${modulePackage}.domain.entity.${domainObjectClassName};
import ${modulePackage}.domain.mapper.${domainObjectClassName}Mapper;
import ${modulePackage}.service.${domainObjectClassName}Service;
import com.github.tanxinzheng.framework.utils.AssertValid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ${domainObjectClassName}ServiceImpl implements ${domainObjectClassName}Service {

    @Resource
    ${domainObjectClassName}Mapper ${domainObjectName}Mapper;

    /**
     * 新增${tableComment}
     *
     * @param ${domainObjectName}Create
     * @return ${domainObjectClassName}Response
     */
    @Transactional
    @Override
    public ${domainObjectClassName}Response create${domainObjectClassName}(${domainObjectClassName} ${domainObjectName}Create) {
        ${domainObjectName}Mapper.insert(${domainObjectName}Create);
        ${domainObjectClassName} ${domainObjectName} = ${domainObjectName}Mapper.selectById(${domainObjectName}Create.getId());
        return ${domainObjectClassName}Response.toResponse(${domainObjectName});
    }

    /**
     * 批量新增${tableComment}
     *
     * @param ${domainObjectName}
     * @return List<${domainObjectClassName}Response>
     */
    @Transactional
    @Override
    public List<${domainObjectClassName}> create${domainObjectClassName}s(List<${domainObjectClassName}> ${domainObjectName}) {
        ${domainObjectName}Mapper.insertBatch(${domainObjectName});
        List<String> ids = ${domainObjectName}.stream().map(${domainObjectClassName}::getId).collect(Collectors.toList());
        return ${domainObjectName}Mapper.selectBatchIds(ids);
    }

    /**
     * 更新${tableComment}
     *
     * @param ${domainObjectName}Update
     * @return ${domainObjectClassName}Response
     */
    @Transactional
    @Override
    public ${domainObjectClassName}Response update${domainObjectClassName}(${domainObjectClassName} ${domainObjectName}Update) {
        ${domainObjectName}Mapper.updateById(${domainObjectName}Update);
        ${domainObjectClassName} ${domainObjectName} = ${domainObjectName}Mapper.selectById(${domainObjectName}Update.getId());
        return ${domainObjectClassName}Response.toResponse(${domainObjectName});
    }

    /**
     * 根据查询参数查询单个对象
     *
     * @param id
     * @return ${domainObjectClassName}Response
     */
    @Override
    public ${domainObjectClassName}Response findOne${domainObjectClassName}Response(String id) {
        ${domainObjectClassName} ${domainObjectName} = ${domainObjectName}Mapper.selectById(id);
        return ${domainObjectClassName}Response.toResponse(${domainObjectName});
    }

    /**
     * 查询${tableComment}领域分页对象（带参数条件）
     *
     * @param ${domainObjectName}Request
     * @return Page<${domainObjectClassName}Response>
     */
    @Override
    public Page<${domainObjectClassName}Response> findPage${domainObjectClassName}Response(${domainObjectClassName}Request ${domainObjectName}Request) {
        Page<${domainObjectClassName}Response> responsePage = new Page<>(${domainObjectName}Request.getPageNum(), ${domainObjectName}Request.getPageSize());
        return ${domainObjectName}Mapper.selectPage(responsePage, ${domainObjectName}Request);
    }

    /**
     * 批量删除${tableComment}
     *
     * @param ids
     * @return int
     */
    @Transactional
    @Override
    public int delete${domainObjectClassName}(List<String> ids) {
        return ${domainObjectName}Mapper.deleteBatchIds(ids);
    }

    /**
     * 删除${tableComment}
     *
     * @param id
     * @return int
     */
    @Transactional
    @Override
    public int delete${domainObjectClassName}(String id) {
        return ${domainObjectName}Mapper.deleteById(id);
    }
}

package ${targetPackage};

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.tanxinzheng.framework.mybatis.domian.QueryParams;
import com.github.tanxinzheng.framework.mybatis.utils.BeanCopierUtils;
import ${modulePackage}.domain.dto.${domainObjectClassName}DTO;
import ${modulePackage}.domain.entity.${domainObjectClassName}DO;
import ${modulePackage}.domain.vo.${domainObjectClassName}VO;
import ${modulePackage}.service.${domainObjectClassName}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

<#include "header.ftl"/>
@Slf4j
@Api(tags = "${tableComment}接口")
@RestController
@RequestMapping(value = "${restMapping}")
public class ${domainObjectClassName}Controller {

    @Resource
    ${domainObjectClassName}Service ${domainObjectName}Service;

    /**
     * 分页查询${tableComment}集合
     * @param queryParams
     * @return
     */
    @ApiOperation(value = "分页查询${tableComment}")
    @GetMapping
    public IPage<${domainObjectClassName}VO> findPage(QueryParams<${domainObjectClassName}DO> queryParams){
        IPage<${domainObjectClassName}DTO> page = ${domainObjectName}Service.findPage(queryParams.getPage(), queryParams.getQueryWrapper());
        return BeanCopierUtils.copy(page, ${domainObjectClassName}VO.class);
    }

    /**
     * 主键查询${tableComment}
     * @param   id  主键
     * @return  ${domainObjectClassName}Response   ${tableComment}领域对象
     */
    @ApiOperation(value = "主键查询${tableComment}")
    @GetMapping(value = "/{id}")
    public ${domainObjectClassName}VO findById(@PathVariable(value = "id") String id){
        ${domainObjectClassName}DTO ${domainObjectName}DTO = ${domainObjectName}Service.findById(id);
        return BeanCopierUtils.copy(${domainObjectName}DTO, ${domainObjectClassName}VO.class);
    }

    /**
     * 新增${tableComment}
     * @param ${domainObjectName}DTO
     * @return
     */
    @ApiOperation(value = "新增${tableComment}")
    @PostMapping
    public ${domainObjectClassName}VO create(@RequestBody @Valid ${domainObjectClassName}DTO ${domainObjectName}DTO) {
        ${domainObjectName}DTO = ${domainObjectName}Service.create${domainObjectClassName}(${domainObjectName}DTO);
        return BeanCopierUtils.copy(${domainObjectName}DTO, ${domainObjectClassName}VO.class);
    }

    /**
     * 更新${tableComment}
     * @param id    主键
     * @param ${domainObjectName}DTO  更新对象参数
     * @return  ${domainObjectClassName}Response   ${tableComment}领域对象
     */
    @ApiOperation(value = "更新${tableComment}")
    @PutMapping(value = "/{id}")
    public boolean update(@PathVariable(value = "id") String id,
                              @RequestBody @Valid ${domainObjectClassName}DTO ${domainObjectName}DTO){
        if(StringUtils.isNotBlank(id)){
            ${domainObjectName}DTO.setId(id);
        }
        return ${domainObjectName}Service.update${domainObjectClassName}(${domainObjectName}DTO);
    }

    /**
     *  删除${tableComment}
     * @param id    主键
     */
    @ApiOperation(value = "删除单个${tableComment}")
    @DeleteMapping(value = "/{id}")
    public boolean delete(@PathVariable(value = "id") String id){
        return ${domainObjectName}Service.delete${domainObjectClassName}(id);
    }

    /**
     *  删除${tableComment}
     * @param ids    查询参数对象
     */
    @ApiOperation(value = "批量删除${tableComment}")
    @DeleteMapping
    public boolean batchDelete(@RequestBody List<String> ids){
        AssertValid.notEmpty(ids, "数组参数不能为空");
        return ${domainObjectName}Service.delete${domainObjectClassName}(ids);
    }


}

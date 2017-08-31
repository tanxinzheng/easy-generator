package ${targetPackage};

import io.swagger.annotations.ApiOperation;
import com.github.pagehelper.Page;
import com.xmomen.framework.logger.ActionLog;
import com.xmomen.framework.web.controller.BaseRestController;
import ${modulePackage}.model.${domainObjectClassName}Query;
import ${modulePackage}.model.${domainObjectClassName}Model;
import ${modulePackage}.service.${domainObjectClassName}Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;
import javax.validation.Valid;
import java.util.List;

<#include "header.ftl">
@RestController
@RequestMapping(value = "${restMapping}")
public class ${domainObjectClassName}Controller extends BaseRestController {

    private static Logger logger = LoggerFactory.getLogger(${domainObjectClassName}Controller.class);

    @Autowired
    ${domainObjectClassName}Service ${domainObjectName}Service;

    /**
     * ${tableComment}列表
     * @param   ${domainObjectName}Query    ${tableComment}查询参数对象
     * @return  Page<${domainObjectClassName}Model> ${tableComment}领域分页对象
     */
    @ApiOperation(value = "查询${tableComment}列表")
    @ActionLog(actionName = "查询${tableComment}列表")
    @PreAuthorize("hasAuthority('${domainObjectClassName?upper_case}:VIEW')")
    @RequestMapping(method = RequestMethod.GET)
    public Page<${domainObjectClassName}Model> get${domainObjectClassName}List(${domainObjectClassName}Query ${domainObjectName}Query){
        return ${domainObjectName}Service.get${domainObjectClassName}ModelPage(${domainObjectName}Query);
    }

    /**
     * 查询单个${tableComment}
     * @param   id  主键
     * @return  ${domainObjectClassName}Model   ${tableComment}领域对象
     */
    @ApiOperation(value = "查询${tableComment}")
    @ActionLog(actionName = "查询${tableComment}")
    @PreAuthorize("hasAuthority('${domainObjectClassName?upper_case}:VIEW')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ${domainObjectClassName}Model get${domainObjectClassName}ById(@PathVariable(value = "id") String id){
        return ${domainObjectName}Service.getOne${domainObjectClassName}Model(id);
    }

    /**
     * 新增${tableComment}
     * @param   ${domainObjectName}Model  新增对象参数
     * @return  ${domainObjectClassName}Model   ${tableComment}领域对象
     */
    @ApiOperation(value = "新增${tableComment}")
    @ActionLog(actionName = "新增${tableComment}")
    @PreAuthorize("hasAuthority('${domainObjectClassName?upper_case}:CREATE')")
    @RequestMapping(method = RequestMethod.POST)
    public ${domainObjectClassName}Model create${domainObjectClassName}(@RequestBody @Valid ${domainObjectClassName}Model ${domainObjectName}Model) {
        return ${domainObjectName}Service.create${domainObjectClassName}(${domainObjectName}Model);
    }

    /**
     * 更新${tableComment}
     * @param id    主键
     * @param ${domainObjectName}Model  更新对象参数
     * @return  ${domainObjectClassName}Model   ${tableComment}领域对象
     */
    @ApiOperation(value = "更新${tableComment}")
    @ActionLog(actionName = "更新${tableComment}")
    @PreAuthorize("hasAuthority('${domainObjectClassName?upper_case}:UPDATE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ${domainObjectClassName}Model update${domainObjectClassName}(@PathVariable(value = "id") String id,
                           @RequestBody @Valid ${domainObjectClassName}Model ${domainObjectName}Model){
        if(StringUtils.isNotBlank(id)){
            ${domainObjectName}Model.set${primaryKeyColumn.columnName?cap_first}(id);
        }
        ${domainObjectName}Service.update${domainObjectClassName}(${domainObjectName}Model);
        return ${domainObjectName}Service.getOne${domainObjectClassName}Model(id);
    }

    /**
     *  删除${tableComment}
     * @param id    主键
     */
    @ApiOperation(value = "删除单个${tableComment}")
    @ActionLog(actionName = "删除单个${tableComment}")
    @PreAuthorize("hasAuthority('${domainObjectClassName?upper_case}:DELETE')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete${domainObjectClassName}(@PathVariable(value = "id") String id){
        ${domainObjectName}Service.delete${domainObjectClassName}(id);
    }

    /**
     *  删除${tableComment}
     * @param ${domainObjectName}Query    查询参数对象
     */
    @ApiOperation(value = "批量删除${tableComment}")
    @ActionLog(actionName = "批量删除${tableComment}")
    @PreAuthorize("hasAuthority('${domainObjectClassName?upper_case}:DELETE')")
    @RequestMapping(method = RequestMethod.DELETE)
    public void delete${domainObjectClassName}s(${domainObjectClassName}Query ${domainObjectName}Query){
        ${domainObjectName}Service.delete${domainObjectClassName}(${domainObjectName}Query.getIds());
    }


}

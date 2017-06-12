package ${targetPackage};

import com.xmomen.framework.mybatis.page.Page;
import com.xmomen.framework.logger.ActionLog;
import ${modulePackage}.model.${domainObjectClassName}Query;
import ${modulePackage}.model.${domainObjectClassName}Model;
import ${modulePackage}.service.${domainObjectClassName}Service;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;
import javax.validation.Valid;
import java.util.List;

<#include "header.ftl">
@RestController
@RequestMapping(value = "${restMapping}")
public class ${domainObjectClassName}Controller {

    public static final String PERMISSION_${domainObjectClassName?upper_case}_CREATE = "${domainObjectName?lower_case}:create";
    public static final String PERMISSION_${domainObjectClassName?upper_case}_DELETE = "${domainObjectName?lower_case}:delete";
    public static final String PERMISSION_${domainObjectClassName?upper_case}_UPDATE = "${domainObjectName?lower_case}:update";
    public static final String PERMISSION_${domainObjectClassName?upper_case}_VIEW   = "${domainObjectName?lower_case}:view";

    @Autowired
    ${domainObjectClassName}Service ${domainObjectName}Service;

    /**
     * ${tableComment}列表
     * @param   ${domainObjectName}Query    ${tableComment}查询参数对象
     * @return  Page<${domainObjectClassName}Model> ${tableComment}领域分页对象
     */
    @RequestMapping(method = RequestMethod.GET)
    @ActionLog(actionName = "查询${tableComment}列表")
    @RequiresPermissions(value = {PERMISSION_${domainObjectClassName?upper_case}_VIEW})
    public Page<${domainObjectClassName}Model> get${domainObjectClassName}List(${domainObjectClassName}Query ${domainObjectName}Query){
        if(${domainObjectName}Query.isPaging()){
            return ${domainObjectName}Service.get${domainObjectClassName}ModelPage(${domainObjectName}Query);
        }
        List<${domainObjectClassName}Model> ${domainObjectName}List = ${domainObjectName}Service.get${domainObjectClassName}ModelList(${domainObjectName}Query);
        return new Page(${domainObjectName}List);
    }

    /**
     * 查询单个${tableComment}
     * @param   id  主键
     * @return  ${domainObjectClassName}Model   ${tableComment}领域对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ActionLog(actionName = "查询${tableComment}")
    @RequiresPermissions(value = {PERMISSION_${domainObjectClassName?upper_case}_VIEW})
    public ${domainObjectClassName}Model get${domainObjectClassName}ById(@PathVariable(value = "id") String id){
        return ${domainObjectName}Service.getOne${domainObjectClassName}Model(id);
    }

    /**
     * 新增${tableComment}
     * @param   ${domainObjectName}Model  新增对象参数
     * @return  ${domainObjectClassName}Model   ${tableComment}领域对象
     */
    @RequestMapping(method = RequestMethod.POST)
    @ActionLog(actionName = "新增${tableComment}")
    @RequiresPermissions(value = {PERMISSION_${domainObjectClassName?upper_case}_CREATE})
    public ${domainObjectClassName}Model create${domainObjectClassName}(@RequestBody @Valid ${domainObjectClassName}Model ${domainObjectName}Model) {
        return ${domainObjectName}Service.create${domainObjectClassName}(${domainObjectName}Model);
    }

    /**
     * 更新${tableComment}
     * @param id    主键
     * @param ${domainObjectName}Model  更新对象参数
     * @return  ${domainObjectClassName}Model   ${tableComment}领域对象
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ActionLog(actionName = "更新${tableComment}")
    @RequiresPermissions(value = {PERMISSION_${domainObjectClassName?upper_case}_UPDATE})
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
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ActionLog(actionName = "删除单个${tableComment}")
    @RequiresPermissions(value = {PERMISSION_${domainObjectClassName?upper_case}_DELETE})
    public void delete${domainObjectClassName}(@PathVariable(value = "id") String id){
        ${domainObjectName}Service.delete${domainObjectClassName}(id);
    }

    /**
     *  删除${tableComment}
     * @param dictionaryQuery    查询参数对象
     */
    @RequestMapping(method = RequestMethod.DELETE)
    @ActionLog(actionName = "批量删除${tableComment}")
    @RequiresPermissions(value = {PERMISSION_${domainObjectClassName?upper_case}_DELETE})
    public void delete${domainObjectClassName}s(${domainObjectClassName}Query ${domainObjectName}Query){
        ${domainObjectName}Service.delete${domainObjectClassName}(${domainObjectName}Query.getIds());
    }


}

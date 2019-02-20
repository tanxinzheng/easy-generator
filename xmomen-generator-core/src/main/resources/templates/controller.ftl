package ${modulePackage}.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.pagehelper.Page;
import ${modulePackage}.model.${domainObjectClassName}Query;
import ${modulePackage}.model.${domainObjectClassName}Model;
import ${modulePackage}.service.${domainObjectClassName}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.commons.lang3.StringUtils;
import javax.validation.Valid;


@Slf4j
@Api(value = "${tableComment}接口", description = "${tableComment}接口")
@RestController
@RequestMapping(value = "${restMapping}")
public class ${domainObjectClassName}Controller {

    @Autowired
    ${domainObjectClassName}Service ${domainObjectName}Service;

    /**
     * ${tableComment}列表
     * @param   ${domainObjectName}Query    ${tableComment}查询参数对象
     * @return  Page<${domainObjectClassName}Model> ${tableComment}领域分页对象
     */
    @ApiOperation(value = "查询${tableComment}列表")
    @GetMapping
    public Page<${domainObjectClassName}Model> get${domainObjectClassName}List(${domainObjectClassName}Query ${domainObjectName}Query){
        return ${domainObjectName}Service.get${domainObjectClassName}ModelPage(${domainObjectName}Query);
    }

    /**
     * 查询单个${tableComment}
     * @param   id  主键
     * @return  ${domainObjectClassName}Model   ${tableComment}领域对象
     */
    @ApiOperation(value = "查询${tableComment}")
    @GetMapping(value = "/{id}")
    public ${domainObjectClassName}Model get${domainObjectClassName}ById(@PathVariable(value = "id") String id){
        return ${domainObjectName}Service.getOne${domainObjectClassName}Model(id);
    }

    /**
     * 新增${tableComment}
     * @param   ${domainObjectName}Model  新增对象参数
     * @return  ${domainObjectClassName}Model   ${tableComment}领域对象
     */
    @ApiOperation(value = "新增${tableComment}")
    @PostMapping
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
    @PutMapping(value = "/{id}")
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
    @DeleteMapping(value = "/{id}")
    public void delete${domainObjectClassName}(@PathVariable(value = "id") String id){
        ${domainObjectName}Service.delete${domainObjectClassName}(id);
    }

    /**
     *  删除${tableComment}
     * @param ${domainObjectName}Query    查询参数对象
     */
    @ApiOperation(value = "批量删除${tableComment}")
    @DeleteMapping
    public void delete${domainObjectClassName}s(${domainObjectClassName}Query ${domainObjectName}Query){
        ${domainObjectName}Service.delete${domainObjectClassName}(${domainObjectName}Query.getIds());
    }


}

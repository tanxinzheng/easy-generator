package ${targetPackage};

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${modulePackage}.domain.dto.${domainObjectClassName}Request;
import ${modulePackage}.domain.dto.${domainObjectClassName}Response;
import ${modulePackage}.service.${domainObjectClassName}Service;
import ${modulePackage}.domain.entity.${domainObjectClassName};
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Api(tags = "${tableComment}接口")
@RestController
@RequestMapping(value = "${restMapping}")
public class ${domainObjectClassName}Controller {

    @Autowired
    ${domainObjectClassName}Service ${domainObjectName}Service;

    /**
     * ${tableComment}列表
     * @param   ${domainObjectName}Request    ${tableComment}查询参数对象
     * @return  Page<${domainObjectClassName}Response> ${tableComment}领域分页对象
     */
    @ApiOperation(value = "查询${tableComment}列表")
    @GetMapping
    public Page<${domainObjectClassName}Response> get${domainObjectClassName}List(${domainObjectClassName}Request ${domainObjectName}Request){
        return ${domainObjectName}Service.findPage${domainObjectClassName}Response(${domainObjectName}Request);
    }

    /**
     * 查询单个${tableComment}
     * @param   id  主键
     * @return  ${domainObjectClassName}Response   ${tableComment}领域对象
     */
    @ApiOperation(value = "查询${tableComment}")
    @GetMapping(value = "/{id}")
    public ${domainObjectClassName}Response get${domainObjectClassName}ById(@PathVariable(value = "id") String id){
        return ${domainObjectName}Service.findOne${domainObjectClassName}Response(id);
    }

    /**
     * 新增${tableComment}
     * @param   ${domainObjectName}Create  新增对象参数
     * @return  ${domainObjectClassName}Response   ${tableComment}领域对象
     */
    @ApiOperation(value = "新增${tableComment}")
    @PostMapping
    public ${domainObjectClassName}Response create${domainObjectClassName}(@RequestBody @Valid ${domainObjectClassName} ${domainObjectName}Create) {
        return ${domainObjectName}Service.create${domainObjectClassName}(${domainObjectName}Create);
    }

    /**
     * 更新${tableComment}
     * @param id    主键
     * @param ${domainObjectName}Update  更新对象参数
     * @return  ${domainObjectClassName}Response   ${tableComment}领域对象
     */
    @ApiOperation(value = "更新${tableComment}")
    @PutMapping(value = "/{id}")
    public ${domainObjectClassName}Response update${domainObjectClassName}(@PathVariable(value = "id") String id,
                           @RequestBody @Valid ${domainObjectClassName} ${domainObjectName}Update){
        if(StringUtils.isNotBlank(id)){
            ${domainObjectName}Update.setId(id);
        }
        return ${domainObjectName}Service.update${domainObjectClassName}(${domainObjectName}Update);
    }

    /**
     *  删除${tableComment}
     * @param id    主键
     */
    @ApiOperation(value = "删除单个${tableComment}")
    @DeleteMapping(value = "/{id}")
    public int delete${domainObjectClassName}(@PathVariable(value = "id") String id){
        return ${domainObjectName}Service.delete${domainObjectClassName}(id);
    }

    /**
     *  删除${tableComment}
     * @param ${domainObjectName}Request    查询参数对象
     */
    @ApiOperation(value = "批量删除${tableComment}")
    @DeleteMapping
    public int delete${domainObjectClassName}s(@RequestBody List<String> ids){
        return ${domainObjectName}Service.delete${domainObjectClassName}(ids);
    }


}

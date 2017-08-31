package ${targetPackage};

import com.xmomen.framework.mybatis.page.PageInterceptor;
import ${modulePackage}.model.${domainObjectClassName};
import ${modulePackage}.mapper.${domainObjectClassName}Mapper;
import ${modulePackage}.model.${domainObjectClassName}Model;
import ${modulePackage}.model.${domainObjectClassName}Query;
import ${modulePackage}.service.${domainObjectClassName}Service;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

<#include "header.ftl">
@Service
public class ${domainObjectClassName}ServiceImpl implements ${domainObjectClassName}Service {

    private static Logger logger = LoggerFactory.getLogger(${domainObjectClassName}ServiceImpl.class);

    @Autowired
    ${domainObjectClassName}Mapper ${domainObjectName}Mapper;

    /**
     * 新增${tableComment}
     *
     * @param ${domainObjectName}Model 新增${tableComment}对象参数
     * @return ${domainObjectClassName}Model    ${tableComment}领域对象
     */
    @Override
    @Transactional
    public ${domainObjectClassName}Model create${domainObjectClassName}(${domainObjectClassName}Model ${domainObjectName}Model) {
        ${domainObjectClassName} ${domainObjectName} = create${domainObjectClassName}(${domainObjectName}Model.getEntity());
        if(${domainObjectName} != null){
            return getOne${domainObjectClassName}Model(${domainObjectName}.getId());
        }
        return null;
    }

    /**
     * 新增${tableComment}实体对象
     *
     * @param ${domainObjectName} 新增${tableComment}实体对象参数
     * @return ${domainObjectClassName} ${tableComment}实体对象
     */
    @Override
    @Transactional
    public ${domainObjectClassName} create${domainObjectClassName}(${domainObjectClassName} ${domainObjectName}) {
        ${domainObjectName}Mapper.insertSelective(${domainObjectName});
        return ${domainObjectName};
    }

    /**
    * 批量新增${tableComment}
    *
    * @param ${domainObjectName}Models 新增${tableComment}对象集合参数
    * @return List<${domainObjectClassName}Model>    ${tableComment}领域对象集合
    */
    @Override
    @Transactional
    public List<${domainObjectClassName}Model> create${domainObjectClassName}s(List<${domainObjectClassName}Model> ${domainObjectName}Models) {
        List<${domainObjectClassName}Model> ${domainObjectName}ModelList = null;
        for (${domainObjectClassName}Model ${domainObjectName}Model : ${domainObjectName}Models) {
            ${domainObjectName}Model = create${domainObjectClassName}(${domainObjectName}Model);
            if(${domainObjectName}Model != null){
                if(${domainObjectName}ModelList == null){
                    ${domainObjectName}ModelList = new ArrayList<>();
                }
                ${domainObjectName}ModelList.add(${domainObjectName}Model);
            }
        }
        return ${domainObjectName}ModelList;
    }

    /**
    * 更新${tableComment}
    *
    * @param ${domainObjectName}Model 更新${tableComment}对象参数
    * @param ${domainObjectName}Query 过滤${tableComment}对象参数
    */
    @Override
    @Transactional
    public void update${domainObjectClassName}(${domainObjectClassName}Model ${domainObjectName}Model, ${domainObjectClassName}Query ${domainObjectName}Query) {
        ${domainObjectName}Mapper.updateSelectiveByQuery(${domainObjectName}Model.getEntity(), ${domainObjectName}Query);
    }

    /**
     * 更新${tableComment}
     *
     * @param ${domainObjectName}Model 更新${tableComment}对象参数
     */
    @Override
    @Transactional
    public void update${domainObjectClassName}(${domainObjectClassName}Model ${domainObjectName}Model) {
        update${domainObjectClassName}(${domainObjectName}Model.getEntity());
    }

    /**
     * 更新${tableComment}实体对象
     *
     * @param ${domainObjectName} 新增${tableComment}实体对象参数
     * @return ${domainObjectClassName} ${tableComment}实体对象
     */
    @Override
    @Transactional
    public void update${domainObjectClassName}(${domainObjectClassName} ${domainObjectName}) {
        ${domainObjectName}Mapper.updateSelective(${domainObjectName});
    }

    /**
     * 删除${tableComment}
     *
     * @param ids 主键数组
     */
    @Override
    @Transactional
    public void delete${domainObjectClassName}(String[] ids) {
        ${domainObjectName}Mapper.deletesByPrimaryKey(Arrays.asList(ids));
    }

    /**
    * 删除${tableComment}
    *
    * @param id 主键
    */
    @Override
    @Transactional
    public void delete${domainObjectClassName}(String id) {
        ${domainObjectName}Mapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询${tableComment}领域分页对象（带参数条件）
     *
     * @param ${domainObjectName}Query 查询参数
     * @return Page<${domainObjectClassName}Model>   ${tableComment}参数对象
     */
    @Override
    public Page<${domainObjectClassName}Model> get${domainObjectClassName}ModelPage(${domainObjectClassName}Query ${domainObjectName}Query) {
        PageInterceptor.startPage(${domainObjectName}Query);
        ${domainObjectName}Mapper.selectModel(${domainObjectName}Query);
        return PageInterceptor.endPage();
    }

    /**
     * 查询${tableComment}领域集合对象（带参数条件）
     *
     * @param ${domainObjectName}Query 查询参数对象
     * @return List<${domainObjectClassName}Model> ${tableComment}领域集合对象
     */
    @Override
    public List<${domainObjectClassName}Model> get${domainObjectClassName}ModelList(${domainObjectClassName}Query ${domainObjectName}Query) {
        return ${domainObjectName}Mapper.selectModel(${domainObjectName}Query);
    }

    /**
     * 查询${tableComment}实体对象
     *
     * @param id 主键
     * @return ${domainObjectClassName} ${tableComment}实体对象
     */
    @Override
    public ${domainObjectClassName} getOne${domainObjectClassName}(String id) {
        return ${domainObjectName}Mapper.selectByPrimaryKey(id);
    }

    /**
     * 根据主键查询单个对象
     *
     * @param id 主键
     * @return ${domainObjectClassName}Model ${tableComment}领域对象
     */
    @Override
    public ${domainObjectClassName}Model getOne${domainObjectClassName}Model(String id) {
        return ${domainObjectName}Mapper.selectModelByPrimaryKey(id);
    }

    /**
     * 根据查询参数查询单个对象（此方法只用于提供精确查询单个对象，若结果数超过1，则会报错）
     *
     * @param ${domainObjectName}Query ${tableComment}查询参数对象
     * @return ${domainObjectClassName}Model ${tableComment}领域对象
     */
    @Override
    public ${domainObjectClassName}Model getOne${domainObjectClassName}Model(${domainObjectClassName}Query ${domainObjectName}Query) throws TooManyResultsException {
        List<${domainObjectClassName}Model> ${domainObjectName}ModelList = ${domainObjectName}Mapper.selectModel(${domainObjectName}Query);
        if(CollectionUtils.isEmpty(${domainObjectName}ModelList)){
            return null;
        }
        if(${domainObjectName}ModelList.size() > 1){
            throw new TooManyResultsException();
        }
        return ${domainObjectName}ModelList.get(0);
    }
}

package ${targetPackage};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.tanxinzheng.framework.mybatis.utils.BeanCopierUtils;
import com.github.tanxinzheng.framework.utils.AssertValid;
import com.google.common.collect.Lists;
import ${modulePackage}.domain.dto.${domainObjectClassName}DTO;
import ${modulePackage}.domain.entity.${domainObjectClassName}DO;
import ${modulePackage}.mapper.${domainObjectClassName}Mapper;
import ${modulePackage}.service.${domainObjectClassName}Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

<#include "header.ftl"/>
@Slf4j
@Service
public class ${domainObjectClassName}ServiceImpl extends ServiceImpl<${domainObjectClassName}Mapper, ${domainObjectClassName}DO> implements ${domainObjectClassName}Service {

    @Resource
    ${domainObjectClassName}Mapper ${domainObjectName}Mapper;

    /**
     * 新增${tableComment}
     *
     * @param ${domainObjectName}DTO
     * @return ${domainObjectClassName}Response
     */
    @Transactional
    @Override
    public ${domainObjectClassName}DTO create${domainObjectClassName}(${domainObjectClassName}DTO ${domainObjectName}DTO) {
        AssertValid.notNull(${domainObjectName}DTO, "${domainObjectName}DTO参数不能为空");
        ${domainObjectClassName}DO ${domainObjectName} = BeanCopierUtils.copy(${domainObjectName}DTO, ${domainObjectClassName}DO.class);

        boolean isOk = save(${domainObjectName});
        if(!isOk){
            return null;
        }
        return BeanCopierUtils.copy(${domainObjectName}, ${domainObjectClassName}DTO.class);
    }


    /**
     * 更新${tableComment}
     *
     * @param ${domainObjectName}DTO
     * @return ${domainObjectClassName}Response
     */
    @Transactional
    @Override
    public boolean update${domainObjectClassName}(${domainObjectClassName}DTO ${domainObjectName}DTO) {
        AssertValid.notNull(${domainObjectName}DTO, "${domainObjectName}DTO不能为空");
        ${domainObjectClassName}DO ${domainObjectName}DO = BeanCopierUtils.copy(${domainObjectName}DTO, ${domainObjectClassName}DO.class);
        return updateById(${domainObjectName}DO);
    }

    /**
     * 批量新增${tableComment}
     * @param ${domainObjectName}s
     * @return
     */
    @Transactional
    @Override
    public List<${domainObjectClassName}DTO> create${domainObjectClassName}s(List<${domainObjectClassName}DTO> ${domainObjectName}s) {
        AssertValid.notEmpty(${domainObjectName}s, "${domainObjectName}s参数不能为空");
        List<${domainObjectClassName}DO> ${domainObjectName}DOList = BeanCopierUtils.copy(${domainObjectName}s, ${domainObjectClassName}DO.class);
        boolean isOK = saveBatch(${domainObjectName}DOList);
        if(!isOK){
            return Lists.newArrayList();
        }
        List<String> ids = ${domainObjectName}DOList.stream().map(${domainObjectClassName}DO::getId).collect(Collectors.toList());
        List<${domainObjectClassName}DO> data = ${domainObjectName}Mapper.selectBatchIds(ids);
        return BeanCopierUtils.copy(data, ${domainObjectClassName}DTO.class);
    }


    /**
     * 主键查询对象
     *
     * @param id
     * @return ${domainObjectClassName}Response
     */
    @Override
    public ${domainObjectClassName}DTO findById(String id) {
        AssertValid.notNull(id, "id参数不能为空");
        ${domainObjectClassName}DO ${domainObjectName} = getById(id);
        return BeanCopierUtils.copy(${domainObjectName}, ${domainObjectClassName}DTO.class);
    }

    /**
    * 查询集合对象
    *
    * @param queryWrapper
    * @return List<${domainObjectClassName}DTO>
    */
    @Override
    public List<${domainObjectClassName}DTO> findList(QueryWrapper<${domainObjectClassName}DO> queryWrapper) {
        return BeanCopierUtils.copy(list(queryWrapper), ${domainObjectClassName}DTO.class);
    }
    /**
     * 查询${tableComment}领域分页对象
     * @param page
     * @param queryWrapper
     * @return
     */
    @Override
    public IPage<${domainObjectClassName}DTO> findPage(IPage<${domainObjectClassName}DO> page, QueryWrapper<${domainObjectClassName}DO> queryWrapper) {
        IPage<${domainObjectClassName}DO> data = (Page<${domainObjectClassName}DO>) page(page, queryWrapper);
        return BeanCopierUtils.copy(data, ${domainObjectClassName}DTO.class);
    }

    /**
     * 批量删除${tableComment}
     *
     * @param ids
     * @return int
     */
    @Transactional
    @Override
    public boolean delete${domainObjectClassName}(List<String> ids) {
        AssertValid.notEmpty(ids, "ids参数不能为空");
        return removeByIds(ids);
    }

    /**
     * 删除${tableComment}
     * @param  id
     * @return
     */
    @Transactional
    @Override
    public boolean delete${domainObjectClassName}(String id) {
        AssertValid.notNull(id, "id参数不能为空");
        return removeById(id);
    }
}

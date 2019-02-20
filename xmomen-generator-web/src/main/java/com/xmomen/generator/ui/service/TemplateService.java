package com.xmomen.generator.ui.service;

import com.xmomen.generator.ui.model.TemplateModel;
import com.xmomen.generator.ui.model.TemplateQuery;

import java.util.List;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
public interface TemplateService {

    /**
     * 添加模板
     * @param templateModel
     * @return
     */
    TemplateModel addTemplate(TemplateModel templateModel);

    /**
     * 更新模板
     * @param id
     * @param templateModel
     * @return
     */
    TemplateModel update(String id, TemplateModel templateModel);

    /**
     *  删除模板
     * @param id
     */
    void deleteTemplate(String id);

    /**
     * 查询模板
     * @param query
     * @return
     */
    List<TemplateModel> getTemplateList(TemplateQuery query);

    /**
     * 查询模板
     * @param id
     * @return
     */
    TemplateModel getTemplateModel(String id);

}

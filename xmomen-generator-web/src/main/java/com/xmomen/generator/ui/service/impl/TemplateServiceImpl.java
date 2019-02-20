package com.xmomen.generator.ui.service.impl;

import com.xmomen.generator.ui.model.TemplateModel;
import com.xmomen.generator.ui.model.TemplateQuery;
import com.xmomen.generator.ui.service.TemplateService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
@Data
@Service
public class TemplateServiceImpl implements TemplateService {


    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 添加模板
     *
     * @param templateModel
     * @return
     */
    @Transactional
    @Override
    public TemplateModel addTemplate(TemplateModel templateModel) {
        mongoTemplate.insert(templateModel);
        return mongoTemplate.findById(templateModel.getId(), TemplateModel.class);
    }

    /**
     * 更新模板
     *
     * @param id
     * @param templateModel
     * @return
     */
    @Override
    public TemplateModel update(String id, TemplateModel templateModel) {
        return null;
    }

    /**
     * 删除模板
     *
     * @param id
     */
    @Override
    public void deleteTemplate(String id) {

    }

    /**
     * 查询模板
     *
     * @param query
     * @return
     */
    @Override
    public List<TemplateModel> getTemplateList(TemplateQuery query) {
        return null;
    }

    /**
     * 查询模板
     *
     * @param id
     * @return
     */
    @Override
    public TemplateModel getTemplateModel(String id) {
        return mongoTemplate.findById(id, TemplateModel.class);
    }
}

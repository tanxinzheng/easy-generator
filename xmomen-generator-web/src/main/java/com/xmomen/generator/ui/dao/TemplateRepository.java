package com.xmomen.generator.ui.dao;

import com.xmomen.generator.ui.model.TemplateModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
public interface TemplateRepository extends MongoRepository<TemplateModel, String>{
}

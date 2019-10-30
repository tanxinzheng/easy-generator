package com.github.tanxinzheng.generator;

import com.github.tanxinzheng.generator.configuration.GeneratorConfiguration;
import com.github.tanxinzheng.generator.model.MongoTemplateType;
import com.github.tanxinzheng.generator.model.TemplateConfig;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
@Slf4j
public class NoSqlGenerator extends AbstractGenerator {

    /**
     * 根据表信息获取字段信息，nosql直接在配置文件中定义字段信息，关系型数据库需获取数据库表结构
     *
     * @param configuration
     */
    @Override
    public void step2(GeneratorConfiguration configuration) {
        configuration.getTables().stream().forEach(tableInfo -> {
            if(CollectionUtils.isNotEmpty(tableInfo.getColumns())){
                tableInfo.getColumns().stream().forEach(columnInfo -> {
                    if(columnInfo.isPrimaryKey()){
                        tableInfo.setPrimaryKeyColumn(columnInfo);
                    }
                });
            }
        });
    }

    /**
     * 根据配置文件获取模板
     *
     * @param configuration
     */
    @Override
    public void step4(GeneratorConfiguration configuration) {
        Map<String, TemplateConfig> templates = Maps.newHashMap();
        for (MongoTemplateType templateType : MongoTemplateType.values()) {
            TemplateConfig templateConfig = new TemplateConfig();
            templateConfig.setFileExt(templateType.getFileExt());
            templateConfig.setTemplateName(templateType.name());
            templateConfig.setPackageName(templateType.getPackageName());
            templateConfig.setTemplateFileName(templateType.getTemplateFileName());
            try {
                if(configuration.getMetadata().getTemplateDirectory() == null){
                    InputStream inputStream = XmomenGenerator.class.getResourceAsStream("/templates/mongo/" + templateConfig.getTemplateFileName());
                    String content = IOUtils.toString(inputStream);
                    templateConfig.setTemplateContent(content);
                }else{
                    File file = new File(configuration.getMetadata().getTemplateDirectory() + templateConfig.getTemplateFileName());
                    String content = FileUtils.readFileToString(file, "UTF-8");
                    templateConfig.setTemplateContent(content);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            templates.put(templateType.name(), templateConfig);
        }
        configuration.getTables().stream().forEach(tableInfo -> {
            tableInfo.setTemplates(Lists.newArrayList(templates.values()));
        });
    }
}

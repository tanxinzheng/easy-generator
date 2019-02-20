package com.xmomen.generator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.model.TemplateConfig;
import com.xmomen.generator.model.TemplateType;
import com.xmomen.generator.utils.FreemarkerUtils;
import com.xmomen.generator.utils.PluginUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
@Slf4j
public abstract class AbstractGenerator {

    public void generate(GeneratorConfiguration configuration){
        // 解析配置文件
        // 自动填充目录相关信息
        step1(configuration);
        // 根据表信息获取字段信息，需定义抽象
        step2(configuration);
        // 填充字段信息
        step3(configuration);
        // 根据配置文件获取模板
        step4(configuration);
        // 填充模板目录信息
        step5(configuration);
        // 根据模板数据渲染模板
        step6(configuration);
        // 生成代码文件
        finalStep(configuration);
//        log.debug(JSONUtils.formatJson(JSONObject.toJSONString(configuration)));
    }

    /**
     * 自动填充目录相关信息
     * @param configuration
     */
    public void step1(GeneratorConfiguration configuration){
        configuration.getTables().stream().forEach(tableInfo -> {
            String domainObjectName = JavaBeansUtil.getCamelCaseString(tableInfo.getTableName(), false);
            if(tableInfo.getDomainObjectName() != null){
                domainObjectName = tableInfo.getDomainObjectName();
            }
            tableInfo.setDomainObjectName(PluginUtils.getLowerCaseString(domainObjectName));
            tableInfo.setDomainObjectUnderlineName(PluginUtils.camelToUnderline(PluginUtils.getLowerCaseString(domainObjectName)));
            tableInfo.setDomainObjectClassName(PluginUtils.getUpperCaseString(domainObjectName));
            tableInfo.setMetadata(configuration.getMetadata());
        });
    }

    /**
     * 根据表信息获取字段信息，nosql直接在配置文件中定义字段信息，关系型数据库需获取数据库表结构
     * @param configuration
     */
    public abstract void step2(GeneratorConfiguration configuration);

    /**
     * 填充字段信息
     * @param configuration
     */
    public void step3(GeneratorConfiguration configuration){
        configuration.getTables().stream().forEach(tableInfo -> {
            tableInfo.getColumns().stream().forEach(columnInfo -> {
                columnInfo.setTableName(tableInfo.getTableName());
            });
        });
    }

    /**
     * 根据配置文件获取模板
     * @param configuration
     */
    public void step4(GeneratorConfiguration configuration){
        Map<String, TemplateConfig> templates = Maps.newHashMap();
        for (TemplateType templateType : TemplateType.values()) {
            TemplateConfig templateConfig = new TemplateConfig();
            templateConfig.setFileExt(templateType.getFileExt());
            templateConfig.setTemplateName(templateType.name());
            templateConfig.setPackageName(templateType.getPackageName());
            templateConfig.setTemplateFileName(templateType.getTemplateFileName());
            try {
                File file = new File(configuration.getMetadata().getTemplateDirectory() + templateConfig.getTemplateFileName());
                String content = FileUtils.readFileToString(file, "UTF-8");
                templateConfig.setTemplateContent(content);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
            templates.put(templateType.name(), templateConfig);
        }
        configuration.getTables().stream().forEach(tableInfo -> {
            tableInfo.setTemplates(Lists.newArrayList(templates.values()));
        });
    }

    /**
     * 填充模板目录信息
     * @param configuration
     */
    public void step5(GeneratorConfiguration configuration){
        configuration.getTables().stream().forEach(tableInfo -> {
            tableInfo.getTemplates().stream().forEach(templateConfig -> {
                templateConfig.setTargetPackage(tableInfo.getModulePackage() + "." + templateConfig.getPackageName());
                templateConfig.setTargetFileName(configuration.getMetadata().getOutputDirectory()
                        + File.separator
                        + templateConfig.getTargetPackage().replace(".", File.separator)
                        + File.separator
                        + tableInfo.getDomainObjectClassName()
                        + templateConfig.getFileExt());
            });
        });
    }

    /**
     * 根据模板数据渲染模板
     * @param configuration
     */
    public void step6(GeneratorConfiguration configuration){
        configuration.getTables().stream().forEach(tableInfo -> {
            tableInfo.getTemplates().stream().forEach(templateConfig -> {
                try {
                    Template t = new Template("name", new StringReader(templateConfig.getTemplateContent()),
                            FreemarkerUtils.getConfiguration());
                    StringWriter stringWriter = new StringWriter();
                    t.process(tableInfo, stringWriter);
                    templateConfig.setTemplateOutput(stringWriter.toString());
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                } catch (TemplateException e) {
                    log.error(e.getMessage(), e);
                }
            });
        });
    }

    /**
     * 生成代码文件
     * @param configuration
     */
    public void finalStep(GeneratorConfiguration configuration){
        configuration.getTables().stream().forEach(tableInfo -> {
            tableInfo.getTemplates().stream().forEach(templateConfig -> {
                try {
                    FileUtils.writeStringToFile(new File(templateConfig.getTargetFileName()), templateConfig.getTemplateOutput(), "UTF-8");
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            });
        });
    }
}

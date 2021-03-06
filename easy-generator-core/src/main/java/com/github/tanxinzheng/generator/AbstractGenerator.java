package com.github.tanxinzheng.generator;

import com.github.tanxinzheng.generator.configuration.GeneratorConfiguration;
import com.github.tanxinzheng.generator.model.ProjectMetadata;
import com.github.tanxinzheng.generator.utils.FreemarkerUtils;
import com.github.tanxinzheng.generator.utils.PluginUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

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
        if(configuration.getMetadata() == null){
            ProjectMetadata metadata = new ProjectMetadata();
            configuration.setMetadata(metadata);
        }
        if(configuration.getMetadata().getRootPath() == null){
            String basedir = new File("").getAbsolutePath();
            configuration.getMetadata().setRootPath(basedir);
        }
        if(configuration.getMetadata().getOutputDirectory() == null){
            configuration.getMetadata().setOutputDirectory(configuration.getMetadata().getRootPath() + "/src/main/java");
        }
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
            if(CollectionUtils.isNotEmpty(tableInfo.getColumns())){
                tableInfo.getColumns().stream().forEach(columnInfo -> {
                    columnInfo.setTableName(tableInfo.getTableName());
                });
            }
        });
    }

    /**
     * 根据配置文件获取模板
     * @param configuration
     */
    public abstract void step4(GeneratorConfiguration configuration);

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
                    tableInfo.setTargetPackage(templateConfig.getTargetPackage());
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

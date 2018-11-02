package com.xmomen.generator;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmomen.generator.configuration.ConfigurationParser;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.mapping.TableMapper;
import com.xmomen.generator.model.ColumnInfo;
import com.xmomen.generator.model.TableInfo;
import com.xmomen.generator.model.TemplateConfig;
import com.xmomen.generator.model.TemplateType;
import com.xmomen.generator.types.JdbcTypeEnums;
import com.xmomen.generator.utils.FreemarkerUtils;
import com.xmomen.generator.utils.PluginUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.db.SqlReservedWords;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Driver;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * Created by tanxinzheng on 17/5/27.
 */
@Slf4j
public class XmomenGenerator {

    private static GeneratorConfiguration generatorConfiguration = null;


    public static int generate(GeneratorConfiguration configuration) throws Exception {
        int generateCount = 0;
        ConfigurationParser.validate();
        if(generatorConfiguration == null){
            generatorConfiguration = configuration;
        }
        if(configuration.getMetadata().getTemplateDirectory() == null){
            configuration.getMetadata().setTemplateDirectory("/templates");
        }
        defineTemplates(configuration);
        defineTables(configuration);
        configuration.getTables().stream().forEach(tableInfo -> {
            tableInfo.getTemplates().stream().forEach(templateConfig -> {
                tableInfo.setTargetPackage(templateConfig.getTargetPackage());
                try {
                    mainGenerate(configuration, tableInfo, templateConfig);
                } catch (IOException | TemplateException e) {
                    log.error(e.getMessage(), e);
                }
            });
        });
        return generateCount;
    }

    public static void defineTables(GeneratorConfiguration configuration) throws Exception {
        DataSource dataSource = new SimpleDriverDataSource(
                getDriver(configuration.getDataSource().getDriver()),
                configuration.getDataSource().getUrl(),
                configuration.getDataSource().getUsername(),
                configuration.getDataSource().getPassword());
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:table.xml"));
        SqlSessionFactory sessionFactory = sqlSessionFactory.getObject();
        sessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        sessionFactory.getConfiguration().setUseColumnLabel(true);
        sessionFactory.getConfiguration().setAutoMappingBehavior(AutoMappingBehavior.FULL);
        sessionFactory.getConfiguration().setMultipleResultSetsEnabled(true);
        SqlSession session = sessionFactory.openSession();
        List<ColumnInfo> columnInfoList;
        switch (configuration.getDataSource().getDialectType()) {
            case MYSQL:
                columnInfoList = session.getMapper(TableMapper.class).getTableInfoByMySQL(configuration);
                break;
            case ORACLE:
                columnInfoList = session.getMapper(TableMapper.class).getTableInfoByOracle(configuration);
                break;
            default:
                throw new IllegalArgumentException("仅支持MySQL，Oracle数据库");
        }
        if(CollectionUtils.isEmpty(columnInfoList)){
            return;
        }
        Map<String, List<ColumnInfo>> collect = columnInfoList.stream().collect(groupingBy(ColumnInfo::getTableName));
        configuration.getTables().stream().forEach(tableInfo -> {
            List<ColumnInfo> columnInfos = collect.get(tableInfo.getTableName());
            Assert.isTrue(CollectionUtils.isNotEmpty(columnInfos), "Not Found the table [ " + tableInfo.getTableName() + " ] information");
            buildColumn(tableInfo, columnInfos);
            String domainObjectName = JavaBeansUtil.getCamelCaseString(tableInfo.getTableName(), false);
            if(tableInfo.getDomainObjectName() != null){
                domainObjectName = tableInfo.getDomainObjectName();
            }
            tableInfo.setTargetPackage(tableInfo.getModulePackage());
            tableInfo.setDomainObjectName(PluginUtils.getLowerCaseString(domainObjectName));
            tableInfo.setDomainObjectUnderlineName(PluginUtils.camelToUnderline(PluginUtils.getLowerCaseString(domainObjectName)));
            tableInfo.setDomainObjectClassName(PluginUtils.getUpperCaseString(domainObjectName));
            setImportClassList(tableInfo);
            buildTableTemplate(configuration, tableInfo);
            tableInfo.setMetadata(configuration.getMetadata());
        });
    }

    /**
     * 定义表字段信息
     * @param tableInfo
     * @param columnInfoList
     */
    private static void buildColumn(TableInfo tableInfo, List<ColumnInfo> columnInfoList){
        for (ColumnInfo columnInfo : columnInfoList) {
            try {
                JdbcTypeEnums jdbcTypeEnums = JdbcTypeEnums.valueOf(columnInfo.getJdbcType());
                jdbcTypeEnums = convertType(jdbcTypeEnums, columnInfo);
                columnInfo.setFullyJavaType(jdbcTypeEnums.getJavaType().getName());
                columnInfo.setJavaType(jdbcTypeEnums.getJavaType().getSimpleName());
                columnInfo.setJdbcType(jdbcTypeEnums.getJdbcType());
            } catch (IllegalArgumentException e) {
                log.error("未找到适配的JdbcType：{}", columnInfo.getJdbcType());
                log.error(e.getMessage(), e);
            }
            columnInfo.setColumnName(PluginUtils.underlineToCamel(columnInfo.getActualColumnName().toLowerCase()));
            if(columnInfo.isPrimaryKey()){
                ColumnInfo primaryKeyColumn = new ColumnInfo();
                BeanUtils.copyProperties(columnInfo, primaryKeyColumn);
                tableInfo.setPrimaryKeyColumn(primaryKeyColumn);
            }
            validateKeyword(tableInfo, columnInfo);
        }
        tableInfo.setColumns(columnInfoList);
    }

    /**
     * 特殊类型转换
     * @param jdbcTypeEnums
     * @param columnInfo
     * @return
     */
    private static JdbcTypeEnums convertType(JdbcTypeEnums jdbcTypeEnums, ColumnInfo columnInfo){
        switch (jdbcTypeEnums){
            case NUMBER:
                if(columnInfo.getScale() == null){
                    jdbcTypeEnums = JdbcTypeEnums.BOOLEAN_DECIMAL;
                }
                break;
            case CHAR:
                if(columnInfo.getScale() == null){
                    jdbcTypeEnums = JdbcTypeEnums.BOOLEAN_CHAR;
                }
                break;
            default:
                break;
        }
        return jdbcTypeEnums;
    }

    /**
     * 定义表模板
     * @param configuration
     * @param tableInfo
     */
    private static void buildTableTemplate(GeneratorConfiguration configuration, TableInfo tableInfo){
        List<TemplateConfig> templateConfigList = Lists.newArrayList();
        configuration.getTemplates().values().stream().forEach(templateConfig -> {
            TemplateConfig template = new TemplateConfig();
            template.setCustom(templateConfig.isCustom());
            template.setIgnore(templateConfig.isIgnore());
            template.setTemplateName(templateConfig.getTemplateName());
            template.setTemplateFileName(templateConfig.getTemplateFileName());
            template.setTargetProject(templateConfig.getTargetProject());
            template.setTargetPackage(tableInfo.getModulePackage() + "." + StringUtils.trim(templateConfig.getTargetPackage()));
            template.setTargetPath(tableInfo.getModulePackage().replace(".", File.separator) +
                    File.separator + StringUtils.trim(templateConfig.getTargetPackage()));
            template.setFileExt(templateConfig.getFileExt());
            template.setTargetFileName(tableInfo.getDomainObjectClassName() + templateConfig.getFileExt());
            templateConfigList.add(template);
        });
        tableInfo.setTemplates(templateConfigList);
    }

    /**
     * 导入依赖类
     * @param tableInfo
     */
    private static void setImportClassList(TableInfo tableInfo){
        Map<String, String> importList = Maps.newHashMap();
        for (ColumnInfo columnInfo : tableInfo.getColumns()) {
            importList.put(columnInfo.getFullyJavaType(), columnInfo.getFullyJavaType());
        }
        tableInfo.setImportClassList(importList);
    }

    /**
     * 定义默认模板
     * @param generatorConfiguration
     */
    private static void defineTemplates(GeneratorConfiguration generatorConfiguration) {
        Map<String, TemplateConfig> templates = Maps.newHashMap();
        for (TemplateType templateType : TemplateType.values()) {
            TemplateConfig templateConfig = new TemplateConfig();
            templateConfig.setFileExt(templateType.getFileExt());
            templateConfig.setTargetPackage(templateType.getTargetPackage());
            templateConfig.setTargetProject(templateType.getTargetProject());
            templateConfig.setTemplateName(templateType.name());
            templateConfig.setTemplateFileName(templateType.getTemplateFileName());
            templates.put(templateType.name(), templateConfig);
        }
        defineCustomTemplate(generatorConfiguration, templates);
        generatorConfiguration.setTemplates(templates);
    }

    /**
     * 定义自定义模板
     * @param configuration
     * @param defaultTemplates
     */
    private static void defineCustomTemplate(GeneratorConfiguration configuration, Map<String, TemplateConfig> defaultTemplates){
        Map<String, TemplateConfig> customTemplates = configuration.getTemplates();
        if(customTemplates == null){
            return;
        }
        for (Map.Entry<String, TemplateConfig> templateConfigEntry : customTemplates.entrySet()) {
            TemplateConfig templateConfig = defaultTemplates.get(templateConfigEntry.getKey());
            if(templateConfig == null){
                // 添加自定义模板到模板列表
                TemplateConfig config = templateConfigEntry.getValue();
                config.setCustom(Boolean.TRUE);
                defaultTemplates.put(templateConfigEntry.getKey(), config);
            }else{
                // 覆盖默认模板参数
                TemplateConfig template = templateConfigEntry.getValue();
                if(template.getTargetProject() != null){
                    templateConfig.setTargetProject(template.getTargetProject());
                }
                if(template.isIgnore()){
                    templateConfig.setIgnore(Boolean.TRUE);
                }
                defaultTemplates.put(templateConfigEntry.getKey(), templateConfig);
            }
        }
        configuration.setTemplates(defaultTemplates);
    }

    private static void mainGenerate(GeneratorConfiguration configuration, TableInfo tableInfo, TemplateConfig templateConfig) throws IOException, TemplateException {
        try {
            if(templateConfig.isIgnore()){
                return;
            }
            Template template = null;
            if(templateConfig.isCustom()){
                template = FreemarkerUtils.getTemplate(templateConfig.getTemplateFileName());
            }else{
                template = FreemarkerUtils.getTemplate(templateConfig.getTemplateFileName(), configuration.getMetadata().getTemplateDirectory());
            }
            File file = new DefaultShellCallback(false).getDirectory(generatorConfiguration.getMetadata().getRootPath()+
                    File.separator +
                    StringUtils.trim(tableInfo.getTargetProject()), templateConfig.getTargetPath());
            Writer writer = new FileWriter(new File(file, templateConfig.getTargetFileName()));
            template.process(tableInfo, writer);
            writer.flush();
            writer.close();
        } catch (ShellException e) {
            log.error("Generate Fail :{0}", JSONObject.toJSONString(tableInfo));
            log.error(e.getMessage(), e);
        }
    }

    private static void validateKeyword(TableInfo tableInfo, ColumnInfo columnInfo){
        if(!generatorConfiguration.getMetadata().isIgnoreKeywordValidate() && SqlReservedWords.containsWord(columnInfo.getActualColumnName())){
            throw new IllegalArgumentException(MessageFormat.format(
                    "The column [{0}.{1}] is database keyword, please change the column name (or add attribute \"ignoreKeywordValidate\":true to metadata)",
                    tableInfo.getTableName(),
                    columnInfo.getActualColumnName()));
        }else if(generatorConfiguration.getMetadata().isIgnoreKeywordValidate() && SqlReservedWords.containsWord(columnInfo.getActualColumnName())){
            columnInfo.setFormatActualColumnName("`" + columnInfo.getActualColumnName()+"`");
        }
    }

    private static Driver getDriver(String driverClass) {
        Driver driver;
        try {
            Class<?> clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(getString("RuntimeError.8"), e); //$NON-NLS-1$
        }
        return driver;
    }

}

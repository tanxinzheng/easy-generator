package com.xmomen.generator;

import com.alibaba.fastjson.JSONObject;
import com.xmomen.generator.configuration.ConfigurationParser;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.mapping.TableMapper;
import com.xmomen.generator.model.ColumnInfo;
import com.xmomen.generator.model.TableInfo;
import com.xmomen.generator.model.TemplateCode;
import com.xmomen.generator.template.TemplateType;
import com.xmomen.maven.plugins.mybatis.generator.plugins.types.JdbcTypeEnums;
import com.xmomen.maven.plugins.mybatis.generator.plugins.utils.FreemarkerUtils;
import com.xmomen.maven.plugins.mybatis.generator.plugins.utils.JSONUtils;
import com.xmomen.maven.plugins.mybatis.generator.plugins.utils.PluginUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.db.SqlReservedWords;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.logging.JdkLoggingImpl;
import org.mybatis.generator.logging.Log;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanxinzheng on 17/5/27.
 */
public class XmomenGenerator {

    protected static Log logger = new JdkLoggingImpl(XmomenGenerator.class);

    private static GeneratorConfiguration generatorConfiguration = null;


    public static int generate(GeneratorConfiguration configuration) throws Exception {
        int generateCount = 0;
        ConfigurationParser.validate();
        if(generatorConfiguration == null){
            generatorConfiguration = configuration;
        }
        DataSource dataSource = new DriverManagerDataSource(
                configuration.getDataSource().getUrl(),
                configuration.getDataSource().getUsername(),
                configuration.getDataSource().getPassword());
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactory sessionFactory = null;
        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:table.xml"));
        sessionFactory = sqlSessionFactory.getObject();
        sessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
        sessionFactory.getConfiguration().setUseColumnLabel(true);
        sessionFactory.getConfiguration().setAutoMappingBehavior(AutoMappingBehavior.FULL);
        sessionFactory.getConfiguration().setMultipleResultSetsEnabled(true);
        SqlSession session = sessionFactory.openSession();
        for (TableInfo config : configuration.getTables()) {
            TableInfo tableInfo = new TableInfo();
            BeanUtils.copyProperties(config, tableInfo);
            List<ColumnInfo> columnInfoList = session.getMapper(TableMapper.class).getTableInfo(tableInfo.getSchema(), tableInfo.getTableName());
            Assert.isTrue(CollectionUtils.isNotEmpty(columnInfoList), "Not Found the table [ " + tableInfo.getTableName() + " ] information.");
            for (ColumnInfo columnInfo : columnInfoList) {
                try {
                    JdbcTypeEnums jdbcTypeEnums = JdbcTypeEnums.valueOf(columnInfo.getJdbcType());
                    columnInfo.setFullyJavaType(jdbcTypeEnums.getJavaType().getName());
                    columnInfo.setJavaType(jdbcTypeEnums.getJavaType().getSimpleName());
                    columnInfo.setJdbcType(jdbcTypeEnums.getJdbcType());
                } catch (IllegalArgumentException ex){
                    logger.error("未找到适配的JdbcType:" + columnInfo.getJdbcType());
                    logger.error(ex.getMessage(), ex);
                }
                columnInfo.setColumnName(PluginUtils.underlineToCamel2(columnInfo.getActualColumnName().toLowerCase()));
                if(columnInfo.isPrimaryKey()){
                    ColumnInfo columnInfo1 = new ColumnInfo();
                    BeanUtils.copyProperties(columnInfo, columnInfo1);
                    tableInfo.setPrimaryKeyColumn(columnInfo1);
                }
                validateKeyword(tableInfo, columnInfo);
            }
            tableInfo.setColumns(columnInfoList);
            logger.debug(JSONObject.toJSONString(tableInfo));
            setParameter(tableInfo);
            System.out.println(JSONUtils.formatJson(JSONObject.toJSONString(tableInfo)));
            Map<String, TemplateCode> templateMap = new HashMap<>();
            // 内置模板
            for (TemplateType templateType : TemplateType.values()) {
                TemplateCode templateCode = new TemplateCode();
                BeanUtils.copyProperties(templateType, templateCode);
                if(configuration.getMetadata().getOverwriteTemplates() != null && configuration.getMetadata().getOverwriteTemplates().containsKey(templateType)){
                    templateCode.setTemplateFileName(generatorConfiguration.getMetadata().getRootPath() + configuration.getMetadata().getOverwriteTemplates().get(templateType));
                    templateCode.setOverwriteTemplate(true);
                }
                templateCode.setCustom(false);
                templateMap.put(templateType.name(), templateCode);
            }
            Map<String, TemplateCode> templateCodeMap = generatorConfiguration.getMetadata().getTemplates();
            // 自定义模板
            if(MapUtils.isNotEmpty(templateCodeMap)){
                for (Map.Entry<String, TemplateCode> templateCodeEntry : templateCodeMap.entrySet()) {
                    String key = templateCodeEntry.getKey();
                    TemplateCode templateCode = new TemplateCode();
                    BeanUtils.copyProperties(templateCodeEntry.getValue(), templateCode);
                    templateCode.setOverwriteTemplate(true);
                    templateCode.setCustom(true);
                    templateCode.setTemplateFileName(generatorConfiguration.getMetadata().getRootPath() + templateCode.getTemplateFileName());
                    templateMap.put(key, templateCode);
                }
            }
            for (Map.Entry<String, TemplateCode> templateCodeEntry : templateMap.entrySet()) {
                String templateCodeKey = templateCodeEntry.getKey();
                TemplateCode templateCode = templateCodeEntry.getValue();
                // 忽略模板
                if(configuration.getMetadata().getIgnoreTemplateTypes() != null && ArrayUtils.contains(configuration.getMetadata().getIgnoreTemplateTypes(), templateCodeKey)){
                    continue;
                }
                // 只生成指定模板
                if(configuration.getMetadata().getTemplateTypes() == null ||
                        (configuration.getMetadata().getTemplateTypes() != null && ArrayUtils.contains(configuration.getMetadata().getTemplateTypes(), templateCodeKey))){
                    // 指定模板文件
                    if(!templateCode.isOverwriteTemplate()){
                        tableInfo.setTemplateFileName(templateCode.getTemplateFileName() + ".ftl");
                    }else{
                        tableInfo.setTemplateFileName(templateCode.getTemplateFileName());
                    }
                    if(templateCode.isWebTemplate()){
                        // 输出目录
                        tableInfo.setTargetFileName(PluginUtils.camelToUnderline(tableInfo.getDomainObjectClassName()) + templateCode.getFileExt());
                    }else{
                        // 输出目录
                        tableInfo.setTargetFileName(tableInfo.getDomainObjectClassName() + templateCode.getFileExt());
                    }
                    // 模块包路径
                    tableInfo.setTargetPackage(tableInfo.getModulePackage() + "." + templateCode.getTargetPackage());
                    tableInfo.setTargetProject(templateCode.getTargetProject());
                    mainGenerate(tableInfo, templateCode.isOverwriteTemplate());
                    generateCount++;
                }
            }
        }
        return generateCount;
    }

    private static TableInfo setParameter(TableInfo tableInfo){
        String tableName = tableInfo.getTableName();
        String camelTableName = JavaBeansUtil.getCamelCaseString(tableInfo.getTableName(), false);
        String domainObjectName = camelTableName;
        if(tableInfo.getDomainObjectName() != null){
            domainObjectName = tableInfo.getDomainObjectName();
        }
        tableInfo.setDomainObjectName(PluginUtils.getLowerCaseString(domainObjectName));
        tableInfo.setDomainObjectUnderlineName(PluginUtils.camelToUnderline(PluginUtils.getLowerCaseString(domainObjectName)));
        tableInfo.setTableName(tableName);
        tableInfo.setDomainObjectClassName(PluginUtils.getUpperCaseString(domainObjectName));
        tableInfo.setTableName(tableName);
        Map<String, String> importList = new HashMap<>();
        for (ColumnInfo introspectedColumn : tableInfo.getColumns()) {
            if(Integer.class.getSimpleName().equals(introspectedColumn.getJavaType())){
                BigDecimal max = new BigDecimal(Math.pow(10, introspectedColumn.getLength())).subtract(BigDecimal.ONE);
                introspectedColumn.setMax(String.valueOf(max));
                if(introspectedColumn.getMax() != null){
                    introspectedColumn.setMin(String.valueOf(max.multiply(BigDecimal.valueOf(-1))));
                }
            }else if(BigDecimal.class.getSimpleName().equals(introspectedColumn.getJavaType())){
                BigDecimal max = new BigDecimal(Math.pow(10, introspectedColumn.getLength())).subtract(BigDecimal.valueOf(1/(Math.pow(10, introspectedColumn.getScale())))).setScale(introspectedColumn.getScale());
                introspectedColumn.setMax(String.valueOf(max));
                if(introspectedColumn.getMax() != null){
                    introspectedColumn.setMin(String.valueOf(max.multiply(BigDecimal.valueOf(-1))));
                }
            }
            importList.put(introspectedColumn.getFullyJavaType(), introspectedColumn.getFullyJavaType());
        }
        tableInfo.setImportClassList(importList);
        return tableInfo;
    }

    private static void mainGenerate(TableInfo tableInfo, boolean overwriteTemplate) throws IOException, TemplateException {
        try {
            Template template = null;
            if(overwriteTemplate){
                template = FreemarkerUtils.getTemplate(tableInfo.getTemplateFileName());
            }else{
                template = FreemarkerUtils.getTemplate(tableInfo.getTemplateFileName(), "/templates");
            }
            File file = new DefaultShellCallback(false).getDirectory(generatorConfiguration.getMetadata().getRootPath()+
                    File.separator +
                    tableInfo.getTargetProject(), tableInfo.getTargetPackage().replace(".", "/"));
            Writer writer = new FileWriter(new File(file, tableInfo.getTargetFileName()));
            template.process(tableInfo, writer);
            writer.flush();
            writer.close();
        } catch (ShellException e) {
            logger.error(MessageFormat.format("Generate Fail :", JSONObject.toJSONString(tableInfo)));
            logger.error(e.getMessage(), e);
            e.printStackTrace();
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
}

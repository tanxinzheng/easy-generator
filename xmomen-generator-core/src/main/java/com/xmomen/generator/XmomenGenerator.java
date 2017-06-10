package com.xmomen.generator;

import com.alibaba.fastjson.JSONObject;
import com.xmomen.generator.configuration.ConfigurationParser;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.mapping.TableMapper;
import com.xmomen.generator.model.ColumnInfo;
import com.xmomen.generator.model.TableInfo;
import com.xmomen.generator.model.TemplateCode;
import com.xmomen.generator.template.TemplateType;
import com.xmomen.maven.plugins.mybatis.generator.plugins.types.JavaTypeResolverDefaultImplExt;
import com.xmomen.maven.plugins.mybatis.generator.plugins.utils.FreemarkerUtils;
import com.xmomen.maven.plugins.mybatis.generator.plugins.utils.JSONUtils;
import com.xmomen.maven.plugins.mybatis.generator.plugins.utils.PluginUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.*;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.exception.ShellException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.logging.JdkLoggingImpl;
import org.mybatis.generator.logging.Log;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.*;
import java.math.BigDecimal;
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
            JavaTypeResolverDefaultImplExt javaTypeResolver = new JavaTypeResolverDefaultImplExt();
            for (ColumnInfo columnInfo : columnInfoList) {
                FullyQualifiedJavaType fullyQualifiedJavaType = javaTypeResolver.getJavaTypeByJdbcTypeName(columnInfo.getJdbcType(), columnInfo);
                columnInfo.setFullyJavaType(fullyQualifiedJavaType.getFullyQualifiedName());
                columnInfo.setJavaType(fullyQualifiedJavaType.getShortName());
                columnInfo.setColumnName(PluginUtils.underlineToCamel2(columnInfo.getActualColumnName().toLowerCase()));
                if(columnInfo.isPrimaryKey()){
                    ColumnInfo columnInfo1 = new ColumnInfo();
                    BeanUtils.copyProperties(columnInfo, columnInfo1);
                    tableInfo.setPrimaryKeyColumn(columnInfo1);
                }
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
                templateCode.setCustom(false);
                templateMap.put(templateType.name(), templateCode);
            }
            // 自定义模板
            templateMap.putAll(generatorConfiguration.getMetadata().getTemplates());
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
                    if(!templateCode.isCustom()){
                        tableInfo.setTemplateFileName(templateCode.getTemplateFileName() + ".ftl");
                    }
                    // 输出目录
                    tableInfo.setTargetFileName(tableInfo.getDomainObjectClassName() + templateCode.getFileExt());
                    // 模块包路径
                    tableInfo.setTargetPackage(tableInfo.getModulePackage() + "." + templateCode.getTargetPackage());
                    tableInfo.setTargetProject(templateCode.getTargetProject());
                    if(configuration.getMetadata().getOverwriteTemplates() != null){
                        mainGenerate(tableInfo, configuration.getMetadata().getOverwriteTemplates().get(templateCodeKey));
                    }else if(templateCode.isCustom()){
                        mainGenerate(tableInfo, templateCode.getTemplateFileName());
                    }else{
                        mainGenerate(tableInfo, null);
                    }
                    generateCount++;
                }
            }
        }
        return generateCount;
    }

    public static TableInfo setParameter(TableInfo tableInfo){
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

    private static void mainGenerate(TableInfo tableInfo, String overwriteTemplate) throws IOException, TemplateException {
        try {
            Template template = null;
            if(overwriteTemplate != null){
                template = FreemarkerUtils.getTemplate(overwriteTemplate);
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
            e.printStackTrace();
        }
    }
}

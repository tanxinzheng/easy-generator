package com.github.tanxinzheng.generator;

import com.github.tanxinzheng.generator.configuration.GeneratorConfiguration;
import com.github.tanxinzheng.generator.jdbc.DatabaseType;
import com.github.tanxinzheng.generator.model.ColumnInfo;
import com.github.tanxinzheng.generator.model.TableInfo;
import com.github.tanxinzheng.generator.model.TemplateConfig;
import com.github.tanxinzheng.generator.model.TemplateType;
import com.github.tanxinzheng.generator.types.JdbcTypeEnums;
import com.github.tanxinzheng.generator.utils.PluginUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
import java.io.IOException;
import java.io.InputStream;
import java.sql.Driver;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * Created by tanxinzheng on 2019/2/20.
 */
@Slf4j
public abstract class SqlGenerator extends AbstractGenerator {

    public abstract DatabaseType getDatabaseType();

    /**
     * 查询字段信息
     * @param configuration
     * @return
     */
    public abstract List<ColumnInfo> selectColumns(SqlSession sqlSession, GeneratorConfiguration configuration);

    /**
     * 根据表信息获取字段信息，nosql直接在配置文件中定义字段信息，关系型数据库需获取数据库表结构
     *
     * @param configuration
     */
    @Override
    public void step2(GeneratorConfiguration configuration) {
        List<ColumnInfo> columnInfoList = Lists.newArrayList();
        try {
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
            SqlSession sqlSession = sessionFactory.openSession();
            columnInfoList = selectColumns(sqlSession, configuration);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if(CollectionUtils.isEmpty(columnInfoList)){
            return;
        }
        Map<String, List<ColumnInfo>> collect = columnInfoList.stream().collect(groupingBy(ColumnInfo::getTableName));
        configuration.getTables().stream().forEach(tableInfo -> {
            List<ColumnInfo> columnInfos = collect.get(tableInfo.getTableName());
            Assert.isTrue(CollectionUtils.isNotEmpty(columnInfos), "Not Found the table [ " + tableInfo.getTableName() + " ] information");
            buildColumn(configuration, tableInfo, columnInfos);
            String domainObjectName = JavaBeansUtil.getCamelCaseString(tableInfo.getTableName(), false);
            if(tableInfo.getDomainObjectName() != null){
                domainObjectName = tableInfo.getDomainObjectName();
            }
            tableInfo.setDomainObjectName(PluginUtils.getLowerCaseString(domainObjectName));
            tableInfo.setDomainObjectUnderlineName(PluginUtils.camelToUnderline(PluginUtils.getLowerCaseString(domainObjectName)));
            tableInfo.setDomainObjectClassName(PluginUtils.getUpperCaseString(domainObjectName));
            setImportClassList(tableInfo);
        });
    }

    @Override
    public void step4(GeneratorConfiguration configuration) {
        Map<String, TemplateConfig> templates = Maps.newHashMap();
        for (TemplateType templateType : TemplateType.values()) {
            TemplateConfig templateConfig = new TemplateConfig();
            templateConfig.setFileExt(templateType.getFileExt());
            templateConfig.setTemplateName(templateType.name());
            templateConfig.setPackageName(templateType.getPackageName());
            templateConfig.setTemplateFileName(templateType.getTemplateFileName());
            try {
                if(configuration.getMetadata().getTemplateDirectory() == null){
                    InputStream inputStream = XmomenGenerator.class.getResourceAsStream("/templates/" + templateConfig.getTemplateFileName());
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
     * 定义表字段信息
     * @param tableInfo
     * @param columnInfoList
     */
    private static void buildColumn(GeneratorConfiguration configuration, TableInfo tableInfo, List<ColumnInfo> columnInfoList){
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
            validateKeyword(configuration, tableInfo, columnInfo);
        }
        tableInfo.setColumns(columnInfoList);
    }

    private static void validateKeyword(GeneratorConfiguration configuration, TableInfo tableInfo, ColumnInfo columnInfo){
        if(configuration.getMetadata().isValidateKeyword()
                && SqlReservedWords.containsWord(columnInfo.getActualColumnName())){
            throw new IllegalArgumentException(MessageFormat.format(
                    "The column [{0}.{1}] is database keyword, If you don't want to pass/valid database keyword, please change the column name (or set attribute \"validateKeyword\":false to metadata)",
                    tableInfo.getTableName(),
                    columnInfo.getActualColumnName()));
        }else if(!configuration.getMetadata().isValidateKeyword() && SqlReservedWords.containsWord(columnInfo.getActualColumnName())){
            columnInfo.setFormatActualColumnName("`" + columnInfo.getActualColumnName()+"`");
        }
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

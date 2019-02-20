package com.xmomen.generator;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.jdbc.DatabaseType;
import com.xmomen.generator.model.ColumnInfo;
import com.xmomen.generator.model.TableInfo;
import com.xmomen.generator.types.JdbcTypeEnums;
import com.xmomen.generator.utils.PluginUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
        if(!configuration.getMetadata().isIgnoreKeywordValidate() && SqlReservedWords.containsWord(columnInfo.getActualColumnName())){
            throw new IllegalArgumentException(MessageFormat.format(
                    "The column [{0}.{1}] is database keyword, please change the column name (or add attribute \"ignoreKeywordValidate\":true to metadata)",
                    tableInfo.getTableName(),
                    columnInfo.getActualColumnName()));
        }else if(configuration.getMetadata().isIgnoreKeywordValidate() && SqlReservedWords.containsWord(columnInfo.getActualColumnName())){
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

package com.xmomen.generator.model;

import com.xmomen.generator.model.ColumnInfo;
import lombok.Data;
import org.mybatis.generator.api.IntrospectedColumn;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by tanxinzheng on 16/8/28.
 */
public @Data class TableInfo {
    private String rootPath;
    private String schema;
    // 表名
    private String tableName;
    // 表名注释
    private String tableComment;
    // 业务领域对象名称
    private String domainObjectName;
    /** 领域对象类 */
    private String domainObjectClassName;
    /** 领域对象下划线名称 */
    private String domainObjectUnderlineName;
    // 模板文件路径
    private String templateFilePath;
    // 模板文件名
    private String templateFileName;
    // 目标文件名称
    private String targetFileName;
    // 模块名
    private String moduleName;
    // 项目目录
    private String targetProject;
    // 包名(包含模块名)
    private String targetPackage;
    // 模块包名
    private String modulePackage;
    // restful 资源映射名称
    private String restMapping;
    // 列集合
    List<ColumnInfo> columns;
    // 导入的class
    private Map<String, String> importClassList;
    // 关键字字段
    private List<ColumnInfo> keywordColumns;
    // 详情界面排除字段
    private List<ColumnInfo> saveExcludeColumns;
    // 需要排除的插件
    private String[] excludePluginsName;
    private boolean isSkip;
    private ColumnInfo primaryKeyColumn;

}

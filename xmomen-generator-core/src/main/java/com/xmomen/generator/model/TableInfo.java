package com.xmomen.generator.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;
import java.util.Map;

/**
 * Created by tanxinzheng on 16/8/28.
 */
public @Data class TableInfo {
    private String schema;
    // 表名
    @NotBlank(message = "表名为必填项")
    private String tableName;
    // 表名注释
    @NotBlank(message = "表注释为必填项（生成代码注释所需）")
    private String tableComment;
    // 业务领域对象名称
    @NotBlank(message = "领域对象名称为必填项")
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
    @NotBlank(message = "目标包路径为必填项（java包路径，如:com.xmomen.module.demo）")
    private String modulePackage;
    // restful 资源映射名称
    @NotBlank(message = "Restful映射接口为必填项（对应Controller类中的RequestMapping，如:/user）")
    private String restMapping;
    // 列集合
    List<ColumnInfo> columns;
    // 导入的class
    private Map<String, String> importClassList;
    // 关键字字段
    private List<ColumnInfo> keywordColumns;
    // 详情界面排除字段
    private List<ColumnInfo> saveExcludeColumns;
    private boolean isSkip;
    private ColumnInfo primaryKeyColumn;
    private List<TemplateConfig> templates;
    private ProjectMetadata metadata;
    /**
     * 是否添加@Excel注解，默认不加
     */
    private boolean addExcelAnnotation;

}

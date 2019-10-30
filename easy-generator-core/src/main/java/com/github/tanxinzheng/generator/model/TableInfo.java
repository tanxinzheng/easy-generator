package com.github.tanxinzheng.generator.model;

import com.github.tanxinzheng.generator.configuration.CommonValidateGroup;
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
    @NotBlank(message = "表名为必填项", groups = {CommonValidateGroup.class})
    private String tableName;
    // 表名注释
    @NotBlank(message = "表注释为必填项（生成代码注释所需）")
    private String tableComment;
    // 业务领域对象名称
    @NotBlank(message = "领域对象名称为必填项", groups = {CommonValidateGroup.class})
    private String domainObjectName;
    /** 领域对象类 */
    private String domainObjectClassName;
    /** 领域对象下划线名称 */
    private String domainObjectUnderlineName;
    // 模块包名
    @NotBlank(message = "目标包路径为必填项（java包路径，如:com.xmomen.module.demo）", groups = {CommonValidateGroup.class})
    private String modulePackage;
    // 文件包路径
    private String targetPackage;
    // restful 资源映射名称
    @NotBlank(message = "Restful映射接口为必填项（对应Controller类中的RequestMapping，如:/user）", groups = {CommonValidateGroup.class})
    private String restMapping;
    // 列集合
    List<ColumnInfo> columns;
    // 导入的class
    private Map<String, String> importClassList;
    // 关键字字段
    private List<ColumnInfo> keywordColumns;
    private boolean isSkip;
    private ColumnInfo primaryKeyColumn;
    private List<TemplateConfig> templates;
    private ProjectMetadata metadata;
    /**
     * 是否添加@Excel注解，默认不加
     */
    private boolean supportExcel;

}

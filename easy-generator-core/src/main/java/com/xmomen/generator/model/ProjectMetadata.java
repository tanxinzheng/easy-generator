package com.xmomen.generator.model;

import lombok.Data;

import java.util.Properties;

/**
 * Created by tanxinzheng on 2018/8/6.
 */
@Data
public class ProjectMetadata {

    private Properties properties;
    private String author;
    /**
     * 模板文件路径 使用jar中自带模板时用到，平台方式无效定义模板目录
     */
    private String templateDirectory;
    private String rootPath;
    private String[] ignoreTemplateTypes;
    private String[] templateTypes;
    private String outputDirectory;
    private boolean ignoreKeywordValidate;

}

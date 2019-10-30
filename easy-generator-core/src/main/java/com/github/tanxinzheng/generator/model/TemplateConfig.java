package com.github.tanxinzheng.generator.model;

import lombok.Data;

/**
 * Created by tanxinzheng on 2018/8/6.
 */
@Data
public class TemplateConfig {

    private boolean custom;
    private boolean ignore;
    // 模板代码
    private String templateName;
    // 生成的文件后缀
    private String fileExt;
    // 模板文件名
    private String templateFileName;
    private String packageName;
    private String targetPackage;
    private String targetFileName;
    private String templateContent;
    private String templateOutput;
}

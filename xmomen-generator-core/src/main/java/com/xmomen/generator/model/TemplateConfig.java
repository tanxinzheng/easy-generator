package com.xmomen.generator.model;

import lombok.Data;

/**
 * Created by tanxinzheng on 2018/8/6.
 */
@Data
public class TemplateConfig {

    private boolean custom;
    private boolean ignore;
    private String templateName;
    private String fileExt;
    private String templateFileName;
    private String targetPackage;
    private String targetProject;
    private String targetPath;
    private String targetFileName;
}

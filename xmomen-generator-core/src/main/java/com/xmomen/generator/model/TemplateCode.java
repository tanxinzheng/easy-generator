package com.xmomen.generator.model;

import lombok.Data;

/**
 * Created by tanxinzheng on 17/6/10.
 */
@Data
public class TemplateCode {

    private String fileExt;
    private String templateFileName;
    private String targetPackage;
    private String targetProject;
    private boolean custom = true;
    private boolean overwriteTemplate;
}

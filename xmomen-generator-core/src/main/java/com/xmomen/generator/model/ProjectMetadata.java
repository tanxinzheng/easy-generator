package com.xmomen.generator.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Properties;

/**
 * Created by tanxinzheng on 2018/8/6.
 */
@Data
public class ProjectMetadata {

    private Properties properties;
    private String author;
    private String templateDirectory;
    @NotBlank
    private String rootPath;
    private String[] ignoreTemplateTypes;
    private String[] templateTypes;
    private String templatesPath;
    private boolean ignoreKeywordValidate;

}

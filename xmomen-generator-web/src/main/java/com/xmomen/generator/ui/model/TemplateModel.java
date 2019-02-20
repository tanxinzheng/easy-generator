package com.xmomen.generator.ui.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by tanxinzheng on 2019/1/6.
 */
@Data
public class TemplateModel implements Serializable {

    private String id;
    private String projectId;
    private String code;
    private String content;
    private String title;
}

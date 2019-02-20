package com.xmomen.generator.ui.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by tanxinzheng on 2019/1/6.
 */
@Data
public class ProjectModel implements Serializable {

    private String code;
    private String name;
    private String description;
    private Datasource datasource;

    @Data
    public class Datasource {

        private String dialect;
        private String driverClass;
        private String url;
        private String username;
        private String password;
    }
}

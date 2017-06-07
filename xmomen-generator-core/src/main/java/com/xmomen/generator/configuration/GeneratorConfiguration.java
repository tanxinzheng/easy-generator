package com.xmomen.generator.configuration;

import com.xmomen.generator.model.TableInfo;
import com.xmomen.generator.template.TemplateType;
import lombok.Data;

import java.util.List;

/**
 * Created by tanxinzheng on 17/5/27.
 */
@Data
public class GeneratorConfiguration {

    private DataSource dataSource;
    private ProjectMetadata metadata;

    private List<TableInfo> tables;

    @Data
    public static class ProjectMetadata {
        private String rootPath;
    }

    @Data
    public static class DataSource {
        private String driver;
        private String url;
        private String username;
        private String password;
    }
}

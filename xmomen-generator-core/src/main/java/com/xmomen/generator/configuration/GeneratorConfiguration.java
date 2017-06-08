package com.xmomen.generator.configuration;

import com.xmomen.generator.model.TableInfo;
import com.xmomen.generator.template.TemplateType;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by tanxinzheng on 17/5/27.
 */
@Data
public class GeneratorConfiguration {

    @Valid
    @NotNull
    private DataSource dataSource;
    @Valid
    private ProjectMetadata metadata;

    @NotNull
    @NotEmpty
    @Valid
    private List<TableInfo> tables;

    @Data
    public static class ProjectMetadata {
        @NotBlank
        private String rootPath;
        private TemplateType[] ignoreTemplateTypes;
        private TemplateType[] templateTypes;
        private String templatePath;
        private Map<TemplateType, String> templates;
    }

    @Data
    public static class DataSource {
        @NotBlank
        private String driver;
        @NotBlank
        private String url;
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

}

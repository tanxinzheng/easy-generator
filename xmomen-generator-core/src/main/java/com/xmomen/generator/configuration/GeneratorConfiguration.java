package com.xmomen.generator.configuration;

import com.xmomen.generator.jdbc.DatabaseType;
import com.xmomen.generator.model.ProjectMetadata;
import com.xmomen.generator.model.TableInfo;
import com.xmomen.generator.model.TemplateConfig;
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
    @NotNull(message = "数据源配置为必填项", groups = {CommonValidateGroup.class})
    private DataSource dataSource;
    @Valid
    private ProjectMetadata metadata;

    @NotNull(message = "数据表配置为必填项", groups = {CommonValidateGroup.class})
    @NotEmpty(message = "数据表配置为必填项", groups = {CommonValidateGroup.class})
    @Valid
    private List<TableInfo> tables;

    private Map<String, TemplateConfig> templates;

    @Data
    public static class DataSource {

        private DatabaseType dialectType;
        @NotBlank(message = "dialect为必填项，且仅支持MySQL，Oracle, MongoDB", groups = {CommonValidateGroup.class})
        private String dialect;
        @NotBlank(message = "driver为必填项", groups = {SQLValidateGroup.class})
        private String driver;
        @NotBlank(message = "url为必填项", groups = {SQLValidateGroup.class})
        private String url;
        @NotBlank(message = "username为必填项", groups = {SQLValidateGroup.class})
        private String username;
        @NotBlank(message = "password为必填项", groups = {SQLValidateGroup.class})
        private String password;

        public DatabaseType getDialectType() {
            if(dialectType == null){
                this.dialectType = DatabaseType.valueOf(dialect.toUpperCase());
            }
            return dialectType;
        }

    }

}

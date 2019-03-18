package com.xmomen.generator.ui.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "PROJECT")
public @Data class ProjectModel implements Serializable {

    /** 主键 */
    @Length(max = 0, message = "主键字符长度限制[0,0]")
    private String id;
    /** 项目名称 */
    @NotBlank(message = "项目名称为必填项")
    @Length(max = 0, message = "项目名称字符长度限制[0,0]")
    private String projectName;
    /** 项目描述 */
    @NotBlank(message = "项目描述为必填项")
    @Length(max = 0, message = "项目描述字符长度限制[0,0]")
    private String description;
    /** 项目代码 */
    @NotBlank(message = "项目代码为必填项")
    @Length(max = 0, message = "项目代码字符长度限制[0,0]")
    private String projectCode;

    /**
    * Get Project Entity Object
    * @return
    */
    @JsonIgnore
    public Project getEntity(){
        Project project = new Project();
        BeanUtils.copyProperties(this, project);
        return project;
    }


}

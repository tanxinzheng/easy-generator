package com.xmomen.generator.model;

/**
 * Created by tanxinzheng on 17/5/27.
 */
public enum TemplateType {
    Entity(".java", "entity.ftl", "model", "src/main/java"),
    Query("Query.java", "queryModel.ftl", "model", "src/main/java"),
    Model("Model.java", "model.ftl", "model", "src/main/java"),
    Mapper("Mapper.java", "mapper.ftl", "mapper", "src/main/java"),
    Service("Service.java", "service.ftl", "service", "src/main/java"),
    ServiceImpl("ServiceImpl.java", "serviceImpl.ftl", "service.impl", "src/main/java"),
    Controller("Controller.java", "controller.ftl", "controller", "src/main/java"),
    MapperXml("Mapper.xml", "mapperXml.ftl", "mapper", "src/main/java")
    ;

    private String fileExt;
    private String templateFileName;
    private String targetPackage;
    private String targetProject;

    TemplateType(String fileExt, String templateFileName, String targetPackage, String targetProject) {
        this.fileExt = fileExt;
        this.templateFileName = templateFileName;
        this.targetPackage = targetPackage;
        this.targetProject = targetProject;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

}

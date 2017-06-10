package com.xmomen.generator.template;

/**
 * Created by tanxinzheng on 17/5/27.
 */
public enum TemplateType {
    Entity(".java", "entity", "model", "src/main/java"),
    Query("Query.java", "queryModel", "model", "src/main/java"),
    Model("Model.java", "model", "model", "src/main/java"),
    Mapper("Mapper.java", "mapper", "mapper", "src/main/java"),
    Service("Service.java", "service", "service", "src/main/java"),
    ServiceImpl("ServiceImpl.java", "serviceImpl", "service.impl", "src/main/java"),
    Controller("Controller.java", "controller", "controller", "src/main/java"),
    MapperXml("Mapper.xml", "mapperXml", "mapper", "src/main/java"),
    ControllerTest("ControllerTest.java", "controllerTest", "controller", "src/test/java")
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

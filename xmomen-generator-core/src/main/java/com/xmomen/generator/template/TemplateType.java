package com.xmomen.generator.template;

/**
 * Created by tanxinzheng on 17/5/27.
 */
public enum TemplateType {
    Entity(".java", "entity", "model", "src/main/java", false),
    Query("Query.java", "queryModel", "model", "src/main/java", false),
    Model("Model.java", "model", "model", "src/main/java", false),
    Mapper("Mapper.java", "mapper", "mapper", "src/main/java", false),
    Service("Service.java", "service", "service", "src/main/java", false),
    ServiceImpl("ServiceImpl.java", "serviceImpl", "service.impl", "src/main/java", false),
    Controller("Controller.java", "controller", "controller", "src/main/java", false),
    MapperXml("Mapper.xml", "mapper-xml", "mapper", "src/main/java", false),
    ControllerTest("ControllerTest.java", "controller-test", "controller", "src/test/java", true)
    ;

    private String fileExt;
    private String templateFileName;
    private String targetPackage;
    private String targetProject;
    private boolean skip;

    TemplateType(String fileExt, String templateFileName, String targetPackage, String targetProject, boolean skip) {
        this.fileExt = fileExt;
        this.templateFileName = templateFileName;
        this.targetPackage = targetPackage;
        this.targetProject = targetProject;
        this.skip = skip;
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

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }
}

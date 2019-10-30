package com.xmomen.generator.model;

/**
 * Created by tanxinzheng on 17/5/27.
 */
public enum MongoTemplateType {
    Query("Query.java", "queryModel.ftl", "model"),
    Model("Model.java", "dtoResponse.ftl", "model"),
    Dao("Dao.java", "dao.ftl", "dao"),
    Service("Service.java", "service.ftl", "service"),
    ServiceImpl("ServiceImpl.java", "serviceImpl.ftl", "service.impl"),
    Controller("Controller.java", "controller.ftl", "controller"),
    ;

    private String fileExt;
    private String templateFileName;
    private String packageName;
    private String targetPackage;
    private String targetProject;

    MongoTemplateType(String fileExt, String templateFileName, String packageName) {
        this.fileExt = fileExt;
        this.templateFileName = templateFileName;
        this.packageName = packageName;
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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}

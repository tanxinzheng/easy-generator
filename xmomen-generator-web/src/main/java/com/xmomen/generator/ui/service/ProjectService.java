package com.xmomen.generator.ui.service;

import com.xmomen.generator.ui.model.ProjectModel;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
public interface ProjectService {

    /**
     * 添加项目
     * @param projectModel
     * @return
     */
    ProjectModel addProject(ProjectModel projectModel);

    /**
     * 修改项目信息
     * @param id
     * @param projectModel
     */
    void updateProject(String id, ProjectModel projectModel);





}

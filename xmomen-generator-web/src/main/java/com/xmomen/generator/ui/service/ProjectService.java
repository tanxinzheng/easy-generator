package com.xmomen.generator.ui.service;

import com.github.pagehelper.Page;
import com.xmomen.generator.ui.model.ProjectQuery;
import com.xmomen.generator.ui.model.ProjectModel;
import com.xmomen.generator.ui.model.Project;
import org.apache.ibatis.exceptions.TooManyResultsException;

import java.util.List;


public interface ProjectService {

    /**
     * 新增项目
     * @param  projectModel   新增项目对象参数
     * @return  ProjectModel    项目领域对象
     */
    public ProjectModel createProject(ProjectModel projectModel);

    /**
     * 新增项目实体对象
     * @param   project 新增项目实体对象参数
     * @return  Project 项目实体对象
     */
    public Project createProject(Project project);

    /**
    * 批量新增项目
    * @param projectModels     新增项目对象集合参数
    * @return List<ProjectModel>    项目领域对象集合
    */
    List<ProjectModel> createProjects(List<ProjectModel> projectModels);

    /**
    * 更新项目
    *
    * @param projectModel 更新项目对象参数
    * @param projectQuery 过滤项目对象参数
    */
    public void updateProject(ProjectModel projectModel, ProjectQuery projectQuery);

    /**
     * 更新项目
     * @param projectModel    更新项目对象参数
     */
    public void updateProject(ProjectModel projectModel);

    /**
     * 更新项目实体对象
     * @param   project 新增项目实体对象参数
     * @return  Project 项目实体对象
     */
    public void updateProject(Project project);

    /**
     * 批量删除项目
     * @param ids   主键数组
     */
    public void deleteProject(String[] ids);

    /**
     * 删除项目
     * @param id   主键
     */
    public void deleteProject(String id);

    /**
     * 查询项目领域分页对象（带参数条件）
     * @param projectQuery 查询参数
     * @return Page<ProjectModel>   项目参数对象
     */
    public Page<ProjectModel> getProjectModelPage(ProjectQuery projectQuery);

    /**
     * 查询项目领域集合对象（带参数条件）
     * @param projectQuery 查询参数对象
     * @return List<ProjectModel> 项目领域集合对象
     */
    public List<ProjectModel> getProjectModelList(ProjectQuery projectQuery);

    /**
     * 查询项目实体对象
     * @param id 主键
     * @return Project 项目实体对象
     */
    public Project getOneProject(String id);

    /**
     * 根据主键查询单个对象
     * @param id 主键
     * @return ProjectModel 项目领域对象
     */
    public ProjectModel getOneProjectModel(String id);

    /**
     * 根据查询参数查询单个对象（此方法只用于提供精确查询单个对象，若结果数超过1，则会报错）
     * @param projectQuery 项目查询参数对象
     * @return ProjectModel 项目领域对象
     */
    public ProjectModel getOneProjectModel(ProjectQuery projectQuery);
}

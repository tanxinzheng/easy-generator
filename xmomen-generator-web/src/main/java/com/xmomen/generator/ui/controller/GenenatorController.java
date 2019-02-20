package com.xmomen.generator.ui.controller;

import com.xmomen.generator.ui.model.ProjectModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
@RestController
@RequestMapping(value = "/generator")
public class GenenatorController {

    /**
     * 菜单配置列表
     * @param   menuQuery    菜单配置查询参数对象
     * @return  Page<MenuModel> 菜单配置领域分页对象
     */
    @GetMapping
    public void getMenuList(ProjectModel projectModel){
//        return menuService.getMenuModelPage(menuQuery);
    }
}

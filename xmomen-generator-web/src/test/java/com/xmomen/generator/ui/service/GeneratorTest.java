package com.xmomen.generator.ui.service;

import com.xmomen.generator.ui.App;
import com.xmomen.generator.ui.model.TemplateModel;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class GeneratorTest {

    @Autowired
    TemplateService templateService;

    private static TemplateModel data;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void generatorTest() throws Exception {
        // 新增项目信息（数据源配置信息）
        // 向项目中添加模板
        // 获取项目数据源数据库表列表
        // 选择需要批量生成的表
        // 获取项目中包含的模板信息列表
        // 选择需要生成的模板集合
        // 保存最终生成的代码文件
    }

}
package com.xmomen.generator.ui.service;

import com.xmomen.generator.ui.App;
import com.xmomen.generator.ui.model.TemplateModel;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TemplateServiceTest {

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
    public void addTemplate() throws Exception {
        String content = FileUtils.readFileToString(new File("src/test/resources/controller.ftl"), "UTF-8");
        TemplateModel templateModel = new TemplateModel();
        templateModel.setCode("CONTROLLER");
        templateModel.setContent(content);
        templateModel.setTitle("Controller模板");
        TemplateModel result = templateService.addTemplate(templateModel);
        data = result;
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void deleteTemplate() throws Exception {
    }

    @Test
    public void getTemplateList() throws Exception {
    }

    @Test
    public void getTemplateModel() throws Exception {
        TemplateModel templateModel = templateService.getTemplateModel(data.getId());
        FileUtils.writeStringToFile(new File("src/test/resources/demo/controller.ftl"), templateModel.getContent());
    }



}
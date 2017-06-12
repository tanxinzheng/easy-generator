package ${targetPackage};

import com.alibaba.fastjson.JSONObject;
import com.xmomen.framework.mybatis.page.Page;
import com.xmomen.module.system.model.${domainObjectClassName}Model;
import com.xmomen.module.test.BaseTestController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.text.MessageFormat;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

<#include "header.ftl">
public class ${domainObjectClassName}ServiceTest extends BaseTestController {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private static String restMapping = "/${domainObjectName}";
    private ${domainObjectClassName}Model ${domainObjectName}Model;
    private ${domainObjectClassName}Model resultModel;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).build();
        if(${domainObjectName}Model == null){
            ${domainObjectName}Model = new ${domainObjectClassName}Model();
        }
    <#list columns as field>
        <#if field['javaType'] = 'String'>
        ${domainObjectName}Model.set${field.columnName?cap_first}(TEST_DATA_STRING);
        <#elseif  field['javaType'] = 'Boolean'>
        ${domainObjectName}Model.set${field.columnName?cap_first}(TEST_DATA_BOOLEAN);
        <#elseif  field['javaType'] = 'Integer'>
        ${domainObjectName}Model.set${field.columnName?cap_first}(TEST_DATA_INTEGER);
        <#elseif  field['javaType'] = 'BigDecimal'>
        ${domainObjectName}Model.set${field.columnName?cap_first}(TEST_DATA_BIG_DECIMAL);
        </#if>
    </#list>
    }

    @Test
    public void testCreate${domainObjectClassName}() throws Exception {
        String params = JSONObject.toJSONString(${domainObjectName}Model);
        ResultActions actions = mockMvc.perform(post(restMapping)
                .contentType(MediaType.APPLICATION_JSON)
                .content(params)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        String resultJson = actions.andReturn().getResponse().getContentAsString();
        ${domainObjectClassName}Model result = JSONObject.parseObject(resultJson, ${domainObjectClassName}Model.class);
        Assert.assertNotNull(result);
        <#list columns as field>
        Assert.assertNotNull(result.get${field.columnName?cap_first}());
        </#list>
        resultModel = result;
    }

    @Test
    public void testGet${domainObjectClassName}ById() throws Exception {
        testCreate${domainObjectClassName}();
        ResultActions actions = mockMvc.perform(get(MessageFormat.format("{0}/{1}",
                restMapping, resultModel.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        String resultJson = actions.andReturn().getResponse().getContentAsString();
        ${domainObjectClassName}Model result = JSONObject.parseObject(resultJson, ${domainObjectClassName}Model.class);
        Assert.assertNotNull(result);
        <#list columns as field>
        Assert.assertNotNull(result.get${field.columnName?cap_first}());
        </#list>
    }

    @Test
    public void testGet${domainObjectClassName}List() throws Exception {
        testCreate${domainObjectClassName}();
        // 列表查询
        ResultActions actions = mockMvc.perform(get(restMapping)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        String resultJson = actions.andReturn().getResponse().getContentAsString();
        HashMap result = JSONObject.parseObject(resultJson, HashMap.class);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.get(DEFAULT_DATA));
        // 分页查询
        ResultActions pagingActions = mockMvc.perform(get(restMapping)
                .contentType(MediaType.APPLICATION_JSON)
                .param(Page.PARAMETER_PAGE_SIZE, String.valueOf(DEFAULT_PAGE_SIZE))
                .param(Page.PARAMETER_PAGE_NUM, String.valueOf(DEFAULT_PAGE_NUM))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        String resultPageJson = pagingActions.andReturn().getResponse().getContentAsString();
        HashMap resultPage = JSONObject.parseObject(resultPageJson, HashMap.class);
        Assert.assertNotNull(resultPage);
        Assert.assertNotNull(resultPage.get(DEFAULT_DATA));
        Assert.assertNotNull(resultPage.get(DEFAULT_PAGE_INFO));
    }

    @Test
    public void testUpdate${domainObjectClassName}() throws Exception {
        testCreate${domainObjectClassName}();
    <#list columns as field>
        <#if !field.primaryKey>
        <#if field['javaType'] = 'String'>
        resultModel.set${field.columnName?cap_first}(TEST_DATA_STRING_UPDATE);
        <#elseif  field['javaType'] = 'Boolean'>
        resultModel.set${field.columnName?cap_first}(TEST_DATA_BOOLEAN_UPDATE);
        <#elseif  field['javaType'] = 'Integer'>
        resultModel.set${field.columnName?cap_first}(TEST_DATA_INTEGER_UPDATE);
        <#elseif  field['javaType'] = 'BigDecimal'>
        resultModel.set${field.columnName?cap_first}(TEST_DATA_BIG_DECIMAL_UPDATE);
        </#if>
        </#if>
    </#list>
        resultModel.setIsShow(false);
        resultModel.setActive(false);
        ResultActions actions = mockMvc.perform(put(MessageFormat.format("{0}/{1}",
                restMapping, resultModel.get${primaryKeyColumn.columnName?cap_first}()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(resultModel))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        String resultJson = actions.andReturn().getResponse().getContentAsString();
        ${domainObjectClassName}Model result = JSONObject.parseObject(resultJson, ${domainObjectClassName}Model.class);
        Assert.assertNotNull(result);
        <#list columns as field>
        Assert.assertNotNull(result.get${field.columnName?cap_first}());
        </#list>
    }

    @Test
    public void testDelete${domainObjectClassName}() throws Exception {
        testCreate${domainObjectClassName}();
        ResultActions actions = mockMvc.perform(delete(MessageFormat.format("{0}/{1}",
                restMapping, resultModel.get${primaryKeyColumn.columnName?cap_first}()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete${domainObjectClassName}s() throws Exception {
        testCreate${domainObjectClassName}();
        ResultActions actions = mockMvc.perform(delete(restMapping)
                .contentType(MediaType.APPLICATION_JSON)
                .param(DEFAULT_BATCH_ID, resultModel.get${primaryKeyColumn.columnName?cap_first}())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
package ${targetPackage};

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

<#include "header.ftl">
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes=MockServletContext.class)
@WebAppConfiguration
public class ${domainObjectClassName}ControllerTest extends MockMvcResultMatchers {

    //模拟mvc对象类.
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new ${domainObjectClassName}Controller()).build();
    }

    @Test
    public void testGet${domainObjectClassName}List() throws Exception {
        RequestBuilder request = null;
        request = MockMvcRequestBuilders.get("/users");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("[]"))
        ;
    }

    @Test
    public void testGet${domainObjectClassName}ById() throws Exception {

    }

    @Test
    public void testCreate${domainObjectClassName}() throws Exception {

    }

    @Test
    public void testUpdate${domainObjectClassName}() throws Exception {

    }

    @Test
    public void testDelete${domainObjectClassName}() throws Exception {

    }

    @Test
    public void testDelete${domainObjectClassName}s() throws Exception {

    }
}
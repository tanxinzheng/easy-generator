package com.xmomen.generator;

import com.xmomen.generator.configuration.GeneratorConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tanxinzheng on 17/8/6.
 */
public class XmomenGeneratorMySQLTest extends XmomenGeneratorTest {
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void generateJson() throws Exception {
        GeneratorConfiguration configuration = super.generate("src/test/resources/generator-config-mysql.json");
        MySqlGenerator generator = new MySqlGenerator();
        generator.generate(configuration);
    }

    @Test
    public void generateYml() throws Exception {
        GeneratorConfiguration configuration = super.generate("src/test/resources/generator-config.yml");
        MySqlGenerator generator = new MySqlGenerator();
        generator.generate(configuration);
    }
}
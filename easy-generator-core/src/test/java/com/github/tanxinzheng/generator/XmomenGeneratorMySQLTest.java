package com.github.tanxinzheng.generator;

import com.github.tanxinzheng.generator.configuration.GeneratorConfiguration;
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
    public void generate() throws Exception {
        GeneratorConfiguration configuration = super.generate("src/test/resources/generator-config-mysql.yml");
        MySqlGenerator generator = new MySqlGenerator();
        generator.generate(configuration);
    }
}
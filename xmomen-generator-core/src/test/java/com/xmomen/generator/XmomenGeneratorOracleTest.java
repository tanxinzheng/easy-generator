package com.xmomen.generator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by tanxinzheng on 17/8/6.
 */
public class XmomenGeneratorOracleTest extends XmomenGeneratorTest {
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
        super.generate("src/test/resources/generator-config-oracle.json");
    }
}
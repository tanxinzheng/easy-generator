package com.xmomen.maven.plugin;

import com.xmomen.generator.utils.PluginUtils;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by tanxinzheng on 17/6/10.
 */
public class GeneratorConfigMySQLMojoTest extends AbstractMojoTestCase {

    private File testPom;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testPom = new File( getBasedir(),"src/test/resources/plugin-help.xml");
    }

    @Test
    public void testExecute() throws Exception {
        //mvn clean test-compile
        GeneratorConfigMySQLMojo mojo = (GeneratorConfigMySQLMojo) lookupMojo ("generate-config-mysql", testPom );
        mojo.execute();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        PluginUtils.deleteDirectory(new File("./src/test/resources/help/generator-config.yml"));
    }

}
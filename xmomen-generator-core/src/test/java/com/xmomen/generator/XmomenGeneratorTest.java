package com.xmomen.generator;

import com.xmomen.generator.configuration.ConfigurationParser;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by tanxinzheng on 17/8/6.
 */
public class XmomenGeneratorTest {
    @Before
    public void setUp() throws Exception {
//

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void generate() throws Exception {
        String basedir = new File("").getAbsolutePath() + File.separator;
        File configFile = new File(basedir, "src/test/resources/generator-config.json");
        GeneratorConfiguration configuration = ConfigurationParser.parserJsonConfig(configFile);
        GeneratorConfiguration.ProjectMetadata projectMetadata = null;
        if(configuration.getMetadata() != null){
            projectMetadata = configuration.getMetadata();
        }else{
            projectMetadata = new GeneratorConfiguration.ProjectMetadata();
            configuration.setMetadata(projectMetadata);
        }
        projectMetadata.setRootPath(basedir);
        XmomenGenerator.generate(configuration);
    }

}
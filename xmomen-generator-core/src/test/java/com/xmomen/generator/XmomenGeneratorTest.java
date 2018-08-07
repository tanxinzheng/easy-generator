package com.xmomen.generator;

import com.xmomen.generator.configuration.ConfigurationParser;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.model.ProjectMetadata;
import com.xmomen.generator.utils.PluginUtils;
import org.junit.After;
import org.junit.Before;

import java.io.File;

/**
 * Created by tanxinzheng on 17/8/6.
 */
public class XmomenGeneratorTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        PluginUtils.deleteDirectory(new File("./src/test/webapp/com"));
        PluginUtils.deleteDirectory(new File("./src/test/java/com/xmomen/module"));
    }

    public void generate(String configPath) throws Exception {
        String basedir = new File("").getAbsolutePath() + File.separator;
        File configFile = new File(basedir, configPath);
        GeneratorConfiguration configuration = ConfigurationParser.parserJsonConfig(configFile);
        ProjectMetadata projectMetadata = null;
        if(configuration.getMetadata() != null){
            projectMetadata = configuration.getMetadata();
        }else{
            projectMetadata = new ProjectMetadata();
            configuration.setMetadata(projectMetadata);
        }
        projectMetadata.setRootPath(basedir);
        XmomenGenerator.generate(configuration);
    }
}
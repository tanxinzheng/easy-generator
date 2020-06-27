package com.github.tanxinzheng.generator;

import com.github.tanxinzheng.generator.configuration.ConfigurationParser;
import com.github.tanxinzheng.generator.configuration.GeneratorConfiguration;
import com.github.tanxinzheng.generator.model.ProjectMetadata;
import com.github.tanxinzheng.generator.utils.PluginUtils;
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
        PluginUtils.deleteDirectory(new File("./src/test/java/com/module/test"));
    }

    public GeneratorConfiguration generate(String configPath) throws Exception {
        String basedir = new File("").getAbsolutePath() + File.separator;
        File configFile = new File(basedir, configPath);
        String absolutePath = configFile.getAbsolutePath();
        GeneratorConfiguration configuration = null;
        if(absolutePath.endsWith(".json")){
            configuration = ConfigurationParser.parserJsonConfig(configFile);
        }else if(absolutePath.endsWith(".yml")){
            configuration = ConfigurationParser.parserYmlConfig(configFile);
        }
        ProjectMetadata projectMetadata = null;
        if(configuration.getMetadata() != null){
            projectMetadata = configuration.getMetadata();
        }else{
            projectMetadata = new ProjectMetadata();
            configuration.setMetadata(projectMetadata);
        }
        System.out.println();
        projectMetadata.setRootPath(basedir);
        return configuration;
    }
}
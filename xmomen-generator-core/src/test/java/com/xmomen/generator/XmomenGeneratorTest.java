package com.xmomen.generator;

import com.xmomen.generator.configuration.ConfigurationParser;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
//        deleteDirectory(new File("./src/test/webapp/com"));
//        deleteDirectory(new File("./src/test/java/com/xmomen/module"));
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

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的目录路径
     * @return
     */
    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();//递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDirectory(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
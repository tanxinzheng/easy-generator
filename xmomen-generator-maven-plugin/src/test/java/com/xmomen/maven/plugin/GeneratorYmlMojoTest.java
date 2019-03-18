package com.xmomen.maven.plugin;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by tanxinzheng on 16/10/16.
 */
public class GeneratorYmlMojoTest extends AbstractMojoTestCase {

    private File testPom;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testPom = new File( getBasedir(),"src/test/resources/plugin-yml.xml");
    }

    @Test
    public void testExecute() throws Exception {
        //mvn clean test-compile
//        GeneratorConfigYmlMojo ymlMojo = (GeneratorConfigYmlMojo) lookupMojo ("generate-yml", testPom );
        GeneratorMojo mojo = (GeneratorMojo) lookupMojo ("generate", testPom );
//        ymlMojo.execute();
        mojo.execute();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        deleteDirectory(new File("./src/test/webapp"));
        deleteDirectory(new File("./src/test/java/com/xmomen/module"));
//        deleteDirectory(new File("./src/main/java/com/xmomen/module"));
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
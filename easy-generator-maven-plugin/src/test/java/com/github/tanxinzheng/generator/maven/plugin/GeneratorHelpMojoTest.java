package com.github.tanxinzheng.generator.maven.plugin;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Created by tanxinzheng on 17/6/10.
 */
public class GeneratorHelpMojoTest extends AbstractMojoTestCase {

    private File testPom;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testPom = new File( getBasedir(),"src/test/resources/plugin-help.xml");
    }

    @Test
    public void testExecute() throws Exception {
        //mvn clean test-compile
        GeneratorHelpMojo mojo = (GeneratorHelpMojo) lookupMojo ("generate-help", testPom );
        mojo.execute();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        deleteDirectory(new File("./src/test/resources/help/generator-help.json"));
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
package com.xmomen.maven.plugin;

import com.xmomen.generator.XmomenGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tanxinzheng on 16/8/26.
 * generate help configuration
 */
@Mojo(name = "generate-help", defaultPhase = LifecyclePhase.NONE)
public class GeneratorHelpMojo extends AbstractMojo {

    /**
     * generator-help
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("------------------------------------------------------------------------");
        getLog().info("Generate Help Json Configuration File ：generator-help.json");
        InputStream is = XmomenGenerator.class.getResourceAsStream("/generator-help.json");
        String basedir = new File("").getAbsolutePath() + File.separator;
        try {
            FileUtils.copyInputStreamToFile(is, new File(basedir, "/src/test/resources/help/generator-help.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

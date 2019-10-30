package com.xmomen.maven.plugin;

import com.xmomen.generator.XmomenGenerator;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * Created by tanxinzheng on 16/8/26.
 *
 */
@Mojo(name = "generate-config", defaultPhase = LifecyclePhase.NONE)
public class GeneratorBaseConfigMojo extends AbstractMojo {

    /**
     * generator-help
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        generateConfig("/generator-config.yml");
    }

    protected void generateConfig(String sourceConfigPath){
        getLog().info("------------------------------------------------------------------------");
        getLog().info(MessageFormat.format("Generate Simple Configuration File : {0}", "generator-config.yml"));
        getLog().info("------------------------------------------------------------------------");
        InputStream is = XmomenGenerator.class.getResourceAsStream(sourceConfigPath);
        String basedir = new File("").getAbsolutePath() + File.separator;
        try {
            FileUtils.copyInputStreamToFile(is, new File(basedir, "/src/test/resources/help/generator-config.yml"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

}

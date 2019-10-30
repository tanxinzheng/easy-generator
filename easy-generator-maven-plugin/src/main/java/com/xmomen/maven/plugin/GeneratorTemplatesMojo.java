package com.xmomen.maven.plugin;

import com.xmomen.generator.XmomenGenerator;
import com.xmomen.generator.model.TemplateType;
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
@Mojo(name = "generate-templates", defaultPhase = LifecyclePhase.NONE)
public class GeneratorTemplatesMojo extends AbstractMojo {

    String basedir = new File("").getAbsolutePath() + File.separator;

    String targetDir = "src\\test\\resources\\help\\templates\\default";

    /**
     * generator-templates
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("------------------------------------------------------------------------");
        getLog().info("Generator default templates file");
        getLog().info("Default Template File Directory: " + basedir + targetDir);
        for (TemplateType templateType : TemplateType.values()) {
            String templateFileName = templateType.getTemplateFileName();
            getLog().info(MessageFormat.format("Template File Name: {0}", templateFileName));
            InputStream is = XmomenGenerator.class.getResourceAsStream("/templates/" + templateFileName);
            try {
                FileUtils.copyInputStreamToFile(is, new File(basedir, targetDir + File.separator + templateFileName));
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }
        getLog().info("------------------------------------------------------------------------");
    }

}

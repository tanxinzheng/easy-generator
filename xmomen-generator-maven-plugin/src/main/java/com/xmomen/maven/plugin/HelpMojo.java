package com.xmomen.maven.plugin;

import com.xmomen.generator.configuration.ConfigurationParser;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.InputStream;

/**
 * Created by tanxinzheng on 16/8/26.
 * print help information
 */
@Mojo(name = "help", defaultPhase = LifecyclePhase.NONE)
public class HelpMojo extends AbstractMojo {

    /**
     * print help information
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        InputStream is = HelpMojo.class.getResourceAsStream("/help.txt");
        String helpInformation = ConfigurationParser.reader(is);
        System.out.println(helpInformation);
    }

}

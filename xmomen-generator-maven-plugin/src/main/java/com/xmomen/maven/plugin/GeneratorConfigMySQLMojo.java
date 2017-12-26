package com.xmomen.maven.plugin;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Created by tanxinzheng on 16/8/26.
 *
 */
@Mojo(name = "generate-config-mysql", defaultPhase = LifecyclePhase.NONE)
public class GeneratorConfigMySQLMojo extends GeneratorBaseConfigMojo {

    /**
     * generator-help
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        generateConfig("/generator-config-mysql.json");
    }

}

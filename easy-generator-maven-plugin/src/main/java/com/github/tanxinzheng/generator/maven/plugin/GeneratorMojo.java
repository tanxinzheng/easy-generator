package com.github.tanxinzheng.generator.maven.plugin;

import com.github.tanxinzheng.generator.XmomenGenerator;
import com.github.tanxinzheng.generator.configuration.GeneratorConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.text.MessageFormat;

/**
 * Created by tanxinzheng on 16/8/26.
 *
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class GeneratorMojo extends AbstractMojo {

    /**
     * Maven Project.
     *
     */
    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    /**
     * Location of the configuration file.
     */
    @Parameter(property = "configurationFile", required = true)
    private String configurationFile;

    /**
     * JDBC Driver to use if a sql.script.file is specified.
     */
    @Parameter(property = "xmomen.generator.jdbcDriver")
    private String jdbcDriver;

    /**
     * JDBC URL to use if a sql.script.file is specified.
     */
    @Parameter(property = "xmomen.generator.jdbcURL")
    private String jdbcURL;

    /**
     * JDBC user ID to use if a sql.script.file is specified.
     */
    @Parameter(property = "xmomen.generator.jdbcUserId")
    private String jdbcUserId;

    /**
     * JDBC password to use if a sql.script.file is specified.
     */
    @Parameter(property = "xmomen.generator.jdbcPassword")
    private String jdbcPassword;

    /**
     * xmomen-generator
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        if(configurationFile == null){
            throw new MojoExecutionException("configurationFiles parameter must be not null");
        }
        generate(configurationFile);
    }

    /**
     * 单模块生成代码
     * @param configurationFile
     */
    private void generate(String configurationFile) throws MojoExecutionException {
        if(configurationFile == null){
            return;
        }
        try {
            String basedir = new File("").getAbsolutePath() + File.separator;
            File configFile = new File(basedir, configurationFile);
            getLog().info("------------------------------------------------------------------------");
            getLog().info(MessageFormat.format("Reading Generator Configuration File ：{0}", configurationFile));
            getLog().info("------------------------------------------------------------------------");
            GeneratorConfiguration configuration = XmomenGenerator.generate(configFile);
            getLog().info("------------------------------------------------------------------------");
            getLog().info(MessageFormat.format("Generate result files output to directory ：{0}", configuration.getMetadata().getOutputDirectory()));
            getLog().info("------------------------------------------------------------------------");
        } catch (Exception e) {
            getLog().error(e.getMessage(), e);
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private void setDataSource(GeneratorConfiguration generatorConfiguration){
        if(StringUtils.isNotBlank(jdbcDriver)){
            generatorConfiguration.getDataSource().setDriver(jdbcDriver);
        }
        if(StringUtils.isNotBlank(jdbcURL)){
            generatorConfiguration.getDataSource().setUrl(jdbcURL);
        }
        if(StringUtils.isNotBlank(jdbcUserId)){
            generatorConfiguration.getDataSource().setUsername(jdbcUserId);
        }
        if(StringUtils.isNotBlank(jdbcPassword)){
            generatorConfiguration.getDataSource().setPassword(jdbcPassword);
        }
    }

}

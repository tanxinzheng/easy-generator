package com.xmomen.maven.plugin;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xmomen.generator.XmomenGenerator;
import com.xmomen.generator.configuration.ConfigurationParser;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.maven.plugins.mybatis.generator.plugins.utils.JSONUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.IOException;

/**
 * Created by tanxinzheng on 16/8/26.
 *
 * @goal generate
 *
 * @execute phase="compile"
 *
 */
public class MybatisGeneratorMojo extends AbstractMojo {

    /**
     * @parameter
     */
    private String configurationFile;

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
    private void generate(String configurationFile){
        getLog().info("xmomen-generator configurationFile: " + configurationFile);
        if(configurationFile == null){
            return;
        }
        try {
            String basedir = new File("").getAbsolutePath() + File.separator;
            File configFile = new File(basedir, configurationFile);
            GeneratorConfiguration configuration = ConfigurationParser.parserJsonConfig(configFile);
            GeneratorConfiguration.ProjectMetadata projectMetadata = new GeneratorConfiguration.ProjectMetadata();
            projectMetadata.setRootPath(basedir);
            configuration.setMetadata(projectMetadata);
            getLog().info("------------------------------------------------------------------------");
            getLog().info("Reading Generator Json Configuration File");
            getLog().info("------------------------------------------------------------------------");
            System.out.println(JSONUtils.formatJson(JSONObject.toJSONString(configuration)));
            XmomenGenerator.generate(configuration);
        } catch (IOException e) {
            getLog().info("未找到配置文件", e);
            e.printStackTrace();
        } catch (JSONException e){
            getLog().info("JSON 配置文件格式不正确", e);
            e.printStackTrace();
        } catch (Exception e) {
            getLog().info("Generate fail.", e);
            e.printStackTrace();
        }

    }

}

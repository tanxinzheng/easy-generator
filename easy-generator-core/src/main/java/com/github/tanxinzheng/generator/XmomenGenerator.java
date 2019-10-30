package com.github.tanxinzheng.generator;

import com.github.tanxinzheng.generator.configuration.CommonValidateGroup;
import com.github.tanxinzheng.generator.configuration.ConfigurationParser;
import com.github.tanxinzheng.generator.configuration.GeneratorConfiguration;
import com.github.tanxinzheng.generator.configuration.SQLValidateGroup;
import com.github.tanxinzheng.generator.jdbc.DatabaseType;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Created by tanxinzheng on 17/5/27.
 */
@Slf4j
public class XmomenGenerator {

    private static GeneratorConfiguration generatorConfiguration = null;


    public static GeneratorConfiguration generate(GeneratorConfiguration configuration) throws Exception {
        ConfigurationParser.validate(configuration, CommonValidateGroup.class);
        if(generatorConfiguration == null){
            generatorConfiguration = configuration;
        }
        DatabaseType databaseType = generatorConfiguration.getDataSource().getDialectType();
        switch (databaseType){
            case MYSQL:
                ConfigurationParser.validate(configuration, SQLValidateGroup.class);
                MySqlGenerator mySqlGenerator = new MySqlGenerator();
                mySqlGenerator.generate(configuration);
                break;
            case ORACLE:
                ConfigurationParser.validate(configuration, SQLValidateGroup.class);
                OracleGenerator oracleGenerator = new OracleGenerator();
                oracleGenerator.generate(configuration);
                break;
            case MONGODB:
                MongodbGenerator mongodbGenerator = new MongodbGenerator();
                mongodbGenerator.generate(configuration);
                break;
            default:
                throw new IllegalArgumentException("未找到适配的数据源类型，现仅支持：MySQL, Oracle, Mongodb");
        }
        return generatorConfiguration;
    }

    /**
     * 根据配置文件路径生成代码
     * @param configurationPath
     * @throws Exception
     */
    public static GeneratorConfiguration generate(String configurationPath) throws Exception {
        return generate(new File(configurationPath));
    }

    /**
     * 根据配置文件路径生成代码
     * @param file
     * @throws Exception
     */
    public static GeneratorConfiguration generate(File file) throws Exception {
        generatorConfiguration = ConfigurationParser.parserConfig(file);
        generate(generatorConfiguration);
        return generatorConfiguration;
    }


}

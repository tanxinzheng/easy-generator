package com.xmomen.generator;

import com.xmomen.generator.configuration.ConfigurationParser;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.jdbc.DatabaseType;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.internal.ObjectFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Driver;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * Created by tanxinzheng on 17/5/27.
 */
@Slf4j
public class XmomenGenerator {

    private static GeneratorConfiguration generatorConfiguration = null;


    public static GeneratorConfiguration generate(GeneratorConfiguration configuration) throws Exception {
        ConfigurationParser.validate(configuration);
        if(generatorConfiguration == null){
            generatorConfiguration = configuration;
        }
        DatabaseType databaseType = generatorConfiguration.getDataSource().getDialectType();
        switch (databaseType){
            case MYSQL:
                MySqlGenerator mySqlGenerator = new MySqlGenerator();
                mySqlGenerator.generate(configuration);
                break;
            case ORACLE:
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

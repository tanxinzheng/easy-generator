package com.xmomen.generator;

import com.xmomen.generator.configuration.ConfigurationParser;
import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.jdbc.DatabaseType;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.internal.ObjectFactory;

import java.sql.Driver;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * Created by tanxinzheng on 17/5/27.
 */
@Slf4j
public class XmomenGenerator {

    private static GeneratorConfiguration generatorConfiguration = null;


    public static void generate(GeneratorConfiguration configuration) throws Exception {
        ConfigurationParser.validate(configuration);
        if(generatorConfiguration == null){
            generatorConfiguration = configuration;
        }
        if(configuration.getMetadata().getTemplateDirectory() == null){
            configuration.getMetadata().setTemplateDirectory("/templates");
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
            case MONGOBD:
                MongodbGenerator mongodbGenerator = new MongodbGenerator();
                mongodbGenerator.generate(configuration);
                break;
            default:
                throw new IllegalArgumentException("未找到适配的数据源类型，现仅支持：MySQL, Oracle, Mongodb");
        }
    }


    private static Driver getDriver(String driverClass) {
        Driver driver;
        try {
            Class<?> clazz = ObjectFactory.externalClassForName(driverClass);
            driver = (Driver) clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(getString("RuntimeError.8"), e); //$NON-NLS-1$
        }
        return driver;
    }

}

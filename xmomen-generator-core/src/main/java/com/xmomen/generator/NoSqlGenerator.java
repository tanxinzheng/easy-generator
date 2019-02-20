package com.xmomen.generator;

import com.xmomen.generator.configuration.GeneratorConfiguration;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tanxinzheng on 2019/2/19.
 */
@Slf4j
public class NoSqlGenerator extends AbstractGenerator {

    /**
     * 根据表信息获取字段信息，nosql直接在配置文件中定义字段信息，关系型数据库需获取数据库表结构
     *
     * @param configuration
     */
    @Override
    public void step2(GeneratorConfiguration configuration) {
        configuration.getTables().stream().forEach(tableInfo -> {
            tableInfo.getColumns().stream().forEach(columnInfo -> {
                if(columnInfo.isPrimaryKey()){
                    tableInfo.setPrimaryKeyColumn(columnInfo);
                }
            });
        });
    }
}

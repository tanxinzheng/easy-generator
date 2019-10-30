package com.github.tanxinzheng.generator.mapping;

import com.github.tanxinzheng.generator.configuration.GeneratorConfiguration;
import com.github.tanxinzheng.generator.model.ColumnInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tanxinzheng on 17/6/7.
 */
@Mapper
public interface TableMapper {

    /**
     * MySQL
     * @param configuration
     * @return
     */
    public List<ColumnInfo> getTableInfoByMySQL(@Param(value = "config") GeneratorConfiguration configuration);

    /**
     * Oracle
     * @param configuration
     * @return
     */
    public List<ColumnInfo> getTableInfoByOracle(@Param(value = "config")GeneratorConfiguration configuration);


    /**
     * PostgreSQL
     * @param configuration
     * @return
     */
    public List<ColumnInfo> getTableInfoByPGSQL(@Param(value = "config")GeneratorConfiguration configuration);

}

package com.xmomen.generator.mapping;

import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.model.ColumnInfo;
import com.xmomen.generator.model.TableInfo;
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
    public List<ColumnInfo> getTableInfoByMySQL(@Param(value = "config")GeneratorConfiguration configuration);

    /**
     * Oracle
     * @param configuration
     * @return
     */
    public List<ColumnInfo> getTableInfoByOracle(@Param(value = "config")GeneratorConfiguration configuration);
}

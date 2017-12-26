package com.xmomen.generator.mapping;

import com.xmomen.generator.model.ColumnInfo;
import com.xmomen.generator.model.TableInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tanxinzheng on 17/6/7.
 */
public interface TableMapper {

    /**
     * MySQL 查询
     * @param schema
     * @param tableName
     * @return
     */
    public List<ColumnInfo> getTableInfoByMySQL(@Param("schema") String schema, @Param("tableName") String tableName);

    /**
     * Oracle 查询
     * @param schema
     * @param tableName
     * @return
     */
    public List<ColumnInfo> getTableInfoByOracle(@Param("schema") String schema, @Param("tableName") String tableName);
}

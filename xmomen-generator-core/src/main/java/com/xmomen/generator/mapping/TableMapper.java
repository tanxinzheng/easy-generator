package com.xmomen.generator.mapping;

import com.xmomen.generator.model.ColumnInfo;
import com.xmomen.generator.model.TableInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by tanxinzheng on 17/6/7.
 */
public interface TableMapper {

    public List<ColumnInfo> getTableInfo(@Param("schema") String schema, @Param("tableName") String tableName);
}

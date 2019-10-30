package com.github.tanxinzheng.generator;

import com.github.tanxinzheng.generator.configuration.GeneratorConfiguration;
import com.github.tanxinzheng.generator.jdbc.DatabaseType;
import com.github.tanxinzheng.generator.mapping.TableMapper;
import com.github.tanxinzheng.generator.model.ColumnInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by tanxinzheng on 2019/2/20.
 */
@Slf4j
public class MySqlGenerator extends SqlGenerator {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }

    /**
     * 查询字段信息
     *
     * @param sqlSession
     * @param configuration
     * @return
     */
    @Override
    public List<ColumnInfo> selectColumns(SqlSession sqlSession, GeneratorConfiguration configuration) {
        return sqlSession.getMapper(TableMapper.class).getTableInfoByMySQL(configuration);
    }
}

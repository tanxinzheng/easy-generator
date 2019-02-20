package com.xmomen.generator;

import com.xmomen.generator.configuration.GeneratorConfiguration;
import com.xmomen.generator.jdbc.DatabaseType;
import com.xmomen.generator.mapping.TableMapper;
import com.xmomen.generator.model.ColumnInfo;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * Created by tanxinzheng on 2019/2/20.
 */
public class OracleGenerator extends SqlGenerator {

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.ORACLE;
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
        return sqlSession.getMapper(TableMapper.class).getTableInfoByOracle(configuration);
    }
}

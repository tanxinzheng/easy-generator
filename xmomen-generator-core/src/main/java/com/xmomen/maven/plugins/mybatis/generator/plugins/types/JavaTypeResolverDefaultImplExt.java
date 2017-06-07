package com.xmomen.maven.plugins.mybatis.generator.plugins.types;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.mybatis.generator.internal.types.Jdbc4Types;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by jengt_000 on 2015/1/25.
 */
public class JavaTypeResolverDefaultImplExt extends JavaTypeResolverDefaultImpl {

    public JavaTypeResolverDefaultImplExt() {
        super();
        properties = new Properties();
        typeMap = new HashMap<Integer, JdbcTypeInformation>();

        typeMap.put(Types.ARRAY, new JdbcTypeInformation("ARRAY", //$NON-NLS-1$
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.BIGINT, new JdbcTypeInformation("BIGINT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Long.class.getName())));
        typeMap.put(Types.BINARY, new JdbcTypeInformation("BINARY", //$NON-NLS-1$
                new FullyQualifiedJavaType("byte[]"))); //$NON-NLS-1$
        typeMap.put(Types.BIT, new JdbcTypeInformation("BIT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Boolean.class.getName())));
        typeMap.put(Types.BLOB, new JdbcTypeInformation("BLOB", //$NON-NLS-1$
                new FullyQualifiedJavaType("byte[]"))); //$NON-NLS-1$
        typeMap.put(Types.BOOLEAN, new JdbcTypeInformation("BOOLEAN", //$NON-NLS-1$
                new FullyQualifiedJavaType(Boolean.class.getName())));
        typeMap.put(Types.CHAR, new JdbcTypeInformation("CHAR", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.CLOB, new JdbcTypeInformation("CLOB", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.DATALINK, new JdbcTypeInformation("DATALINK", //$NON-NLS-1$
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.DATE, new JdbcTypeInformation("DATE", //$NON-NLS-1$
                new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.DISTINCT, new JdbcTypeInformation("DISTINCT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.DOUBLE, new JdbcTypeInformation("DOUBLE", //$NON-NLS-1$
                new FullyQualifiedJavaType(BigDecimal.class.getName()))); // DOUBLE -> Double 修改 DOUBLE -> BigDecimal
        typeMap.put(Types.FLOAT, new JdbcTypeInformation("FLOAT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Double.class.getName())));
        typeMap.put(Types.INTEGER, new JdbcTypeInformation("INTEGER", //$NON-NLS-1$
                new FullyQualifiedJavaType(Integer.class.getName())));
        typeMap.put(Types.JAVA_OBJECT, new JdbcTypeInformation("JAVA_OBJECT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Jdbc4Types.LONGNVARCHAR, new JdbcTypeInformation("LONGNVARCHAR", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.LONGVARBINARY, new JdbcTypeInformation(
                "LONGVARBINARY", //$NON-NLS-1$
                new FullyQualifiedJavaType("byte[]"))); //$NON-NLS-1$
        typeMap.put(Types.LONGVARCHAR, new JdbcTypeInformation("LONGVARCHAR", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Jdbc4Types.NCHAR, new JdbcTypeInformation("NCHAR", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Jdbc4Types.NCLOB, new JdbcTypeInformation("NCLOB", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Jdbc4Types.NVARCHAR, new JdbcTypeInformation("NVARCHAR", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName())));
        typeMap.put(Types.NULL, new JdbcTypeInformation("NULL", //$NON-NLS-1$
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.OTHER, new JdbcTypeInformation("VARCHAR", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName()))); // OTHER 修改为 VARCHAR -> String
        typeMap.put(Types.REAL, new JdbcTypeInformation("REAL", //$NON-NLS-1$
                new FullyQualifiedJavaType(Float.class.getName())));
        typeMap.put(Types.REF, new JdbcTypeInformation("REF", //$NON-NLS-1$
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Short.class.getName())));
        typeMap.put(Types.STRUCT, new JdbcTypeInformation("STRUCT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Object.class.getName())));
        typeMap.put(Types.TIME, new JdbcTypeInformation("TIME", //$NON-NLS-1$
                new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP", //$NON-NLS-1$
                new FullyQualifiedJavaType(Date.class.getName())));
        typeMap.put(Types.INTEGER, new JdbcTypeInformation("INT", //$NON-NLS-1$
                new FullyQualifiedJavaType(Integer.class.getName())));// TINYINT -> Byte 修改为 TINYINT -> Integer
        typeMap.put(Types.VARBINARY, new JdbcTypeInformation("VARBINARY", //$NON-NLS-1$
                new FullyQualifiedJavaType("byte[]"))); //$NON-NLS-1$
        typeMap.put(Types.VARCHAR, new JdbcTypeInformation("VARCHAR", //$NON-NLS-1$
                new FullyQualifiedJavaType(String.class.getName())));

    }

    /**
     * 根据jdbcTypeName得到javaType
     * @param jdbcTypeName
     * @return
     */
    public FullyQualifiedJavaType getJavaTypeByJdbcTypeName(String jdbcTypeName){
        FullyQualifiedJavaType fullyQualifiedJavaType = null;
        for (JdbcTypeInformation jdbcTypeInformation : typeMap.values()) {
            if(jdbcTypeInformation.getJdbcTypeName().equalsIgnoreCase(jdbcTypeName)){
                fullyQualifiedJavaType = jdbcTypeInformation.getFullyQualifiedJavaType();
                break;
            }
        }
        return fullyQualifiedJavaType;
    }

    public FullyQualifiedJavaType calculateJavaType(
            IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType answer;
        JdbcTypeInformation jdbcTypeInformation = typeMap
                .get(introspectedColumn.getJdbcType());

        if (jdbcTypeInformation == null) {
            switch (introspectedColumn.getJdbcType()) {
                case Types.DECIMAL:
                case Types.NUMERIC:
                    if (introspectedColumn.getScale() > 0
                            || introspectedColumn.getLength() > 18
                            || forceBigDecimals) {
                        answer = new FullyQualifiedJavaType(BigDecimal.class
                                .getName());
                    } else if (introspectedColumn.getLength() > 9) {
                        answer = new FullyQualifiedJavaType(Long.class.getName());
                    } else if (introspectedColumn.getLength() > 4) {
                        answer = new FullyQualifiedJavaType(Integer.class.getName());
                    } else {
                        answer = new FullyQualifiedJavaType(Short.class.getName());
                    }
                    break;

                default:
                    answer = null;
                    break;
            }
        } else if (Types.CHAR == introspectedColumn.getJdbcType()) {
            if (introspectedColumn.getLength() == 1) {
                answer = new FullyQualifiedJavaType(Boolean.class.getName());
            } else {
                answer = new FullyQualifiedJavaType(String.class.getName());
            }
        } else {
            answer = jdbcTypeInformation.getFullyQualifiedJavaType();
        }

        return answer;
    }

}

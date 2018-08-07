package com.xmomen.generator.types;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by tanxinzheng on 17/8/23.
 */
public enum JdbcTypeEnums {

    INT(Integer.class, "INTEGER"),
    TINYINT(Boolean.class, "BIT"),
    CHAR(String.class, "VARCHAR"),
    VARCHAR(String.class, "VARCHAR"),
    VARCHAR2(String.class, "VARCHAR"),
    NVARCHAR2(String.class, "VARCHAR"),
    BIGINT(Long.class, "BIGINT"),
    DATE(Date.class, "DATE"),
    DATETIME(Date.class, "TIMESTAMP"),
    TIMESTAMP(Date.class, "TIMESTAMP"),
    LONG(Long.class, "DECIMAL"),
    BOOLEAN_DECIMAL(Boolean.class, "DECIMAL"),
    DECIMAL(BigDecimal.class, "DECIMAL"),
    NUMBER(BigDecimal.class, "DECIMAL"),
    DOUBLE(BigDecimal.class, "DECIMAL"),
    BIGDECIMAL(BigDecimal.class, "DECIMAL"),
    TEXT(String.class, "VARCHAR");

    private Class javaType;
    private String jdbcType;

    JdbcTypeEnums(Class javaType, String jdbcType) {
        this.javaType = javaType;
        this.jdbcType = jdbcType;
    }

    public Class getJavaType() {
        return javaType;
    }

    public void setJavaType(Class javaType) {
        this.javaType = javaType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }
}

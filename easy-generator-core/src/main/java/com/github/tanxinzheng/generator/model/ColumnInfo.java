package com.github.tanxinzheng.generator.model;

import lombok.Data;

/**
 * Created by tanxinzheng on 17/5/27.
 */
@Data
public class ColumnInfo {

    private String tableName;

    private String columnName;

    private String columnComment;

    private String actualColumnName;

    private String formatActualColumnName;

    private String jdbcType;

    private String javaType;

    private String fullyJavaType;

    private boolean nullable;

    private int length;

    private Integer scale;

    private String max;

    private String min;

    private boolean isPrimaryKey;

    private String defaultValue;

    private boolean hide;

}

package org.yu.generate.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangxd
 * @date 2023-04-19 23:36
 */
public final class GeneratorConst {
    public static final String DEFAULT_PKG_NAME = "com.example.myapp";
    public static final String QUERY_TABLE_NAMES = "QUERY_TABLE_NAMES";
    public static final String QUERY_TABLE_COLUMNS = "QUERY_TABLE_COLUMNS";

    public final static Map<String, String> javaBoxTypeMap = new HashMap<>();
    public final static Map<String, String> importsTypeMap = new HashMap<>();

    static {
        javaBoxTypeMap.put("bigint", "Long");
        javaBoxTypeMap.put("binary", "Byte[]");
        javaBoxTypeMap.put("bit", "Boolean");
        javaBoxTypeMap.put("blob", "Byte[]");
        javaBoxTypeMap.put("mediumblob", "Byte[]");
        javaBoxTypeMap.put("longblob", "Byte[]");
        javaBoxTypeMap.put("char", "String");
        javaBoxTypeMap.put("date", "Date");
        javaBoxTypeMap.put("datetime", "Date");
        javaBoxTypeMap.put("decimal", "BigDecimal");
        javaBoxTypeMap.put("double", "Double");
        javaBoxTypeMap.put("float", "Float");
        javaBoxTypeMap.put("int", "Integer");
        javaBoxTypeMap.put("int4", "Integer");
        javaBoxTypeMap.put("int8", "Long");
        javaBoxTypeMap.put("image", "Byte[]");
        javaBoxTypeMap.put("money", "BigDecimal");
        javaBoxTypeMap.put("nchar", "String");
        javaBoxTypeMap.put("ntext", "String");
        javaBoxTypeMap.put("numeric", "BigDecimal");
        javaBoxTypeMap.put("nvarchar", "String");
        javaBoxTypeMap.put("nvarchar2", "String");
        javaBoxTypeMap.put("real", "Float");
        javaBoxTypeMap.put("smalldatetime", "Date");
        javaBoxTypeMap.put("smallint", "Integer");
        javaBoxTypeMap.put("smallmoney", "BigDecimal");
        javaBoxTypeMap.put("sql_variant", "String");
        javaBoxTypeMap.put("text", "String");
        javaBoxTypeMap.put("tinyint", "Integer");
        javaBoxTypeMap.put("uniqueidentifier", "String");
        javaBoxTypeMap.put("varbinary", "Byte[]");
        javaBoxTypeMap.put("varchar", "String");
        javaBoxTypeMap.put("varchar2", "String");
        javaBoxTypeMap.put("number", "Long");
        javaBoxTypeMap.put("timestamp", "Date");
        javaBoxTypeMap.put("timestamp(6)", "Date");

        importsTypeMap.put("Date", "java.util.Date");
        importsTypeMap.put("BigDecimal", "java.math.BigDecimal");
    }
}
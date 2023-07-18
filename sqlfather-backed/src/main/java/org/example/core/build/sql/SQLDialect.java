package org.example.core.build.sql;

/**
 * Author: ZhaoXing

 */

/**
 * sql 方言
 */
public interface SQLDialect {

    /**
     * 封装表名
     */
    String wrapTableName(String tableName);

    /**
     * 解析表名
     *
     * @param tableName
     * @return
     */
    String parseTableName(String tableName);

    /**
     * 封装字段名
     */
    String wrapFieldName(String fieldName);

    /**
     * 解析字段名
     *
     * @param fieldName
     * @return
     */
    String parseFieldName(String fieldName);
}

package org.example.core.schema;

/**
 * Author: ZhaoXing

 */

import lombok.Data;

import java.util.List;

/**
 * 表概要
 */
@Data
public class TableSchema {
    /**
     * 数据库名
     */
    private String dbName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableNote;

    /**
     * 模拟数据条数
     */
    private Integer mockNum;

    /**
     * 列信息列表
     */
    private List<Field> fieldList;

    /**
     * 列信息
     */
    @Data
    public static class Field{

        /**
         * 字段名
         */
        private String fieldName;

        /**
         * 字段类型
         */
        private String fieldType;

        /**
         * 默认值
         */
        private String defaultValue;

        /**
         * 注释
         */
        private String note;

        /**
         * 是否为主键
         */
        private boolean primaryKey;

        /**
         * 是否自增
         */
        private boolean autoIncrement;

        /**
         * 是否不为空
         */
        private boolean notNull;


        /**
         * 模拟类型（随机、图片、规则、词库）
         */
        private String mockType;

        /**
         * 模拟参数
         */
        private String mockParam;

        /**
         * 附加条件
         */
        private String other;

    }


}

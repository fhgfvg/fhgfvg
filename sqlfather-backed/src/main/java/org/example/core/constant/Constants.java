package org.example.core.constant;

/**
 * Author: ZhaoXing

 */
public interface Constants {

    /**
     * 默认生成的假数据条数
     */
    public static final int SCHEMA_MOCKNUM = 20;

    /**
     * 创建表SQL模板
     */
    public static final String create_table_template = "%s create table id not exists %s ( %s ) %s;";
}

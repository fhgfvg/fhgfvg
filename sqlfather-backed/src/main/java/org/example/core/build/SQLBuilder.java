package org.example.core.build;

/**
 * Author: ZhaoXing

 */

import cn.hutool.core.util.StrUtil;
import io.common.enums.ErrorCode;
import io.common.exceptions.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.example.core.build.sql.MySQLDialect;
import org.example.core.build.sql.SQLDialect;
import org.example.core.build.sql.SQLDialectFactory;
import org.example.core.constant.Constants;
import org.example.core.model.enums.FieldTypeEnum;
import org.example.core.model.enums.MockTypeEnum;
import org.example.core.schema.TableSchema;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * SQL 生成器
 */
@Slf4j
public class SQLBuilder {

    /**
     * 方言
     */
    private SQLDialect sqlDialect;

    /**
     * 默认为MYSQL方言
     */
    public SQLBuilder() {
        this.sqlDialect = SQLDialectFactory.getDialect(MySQLDialect.class.getName());
    }

    /**
     * 其他方言实现
     *
     * @param sqlDialect
     */
    public SQLBuilder(SQLDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    /**
     * 设置方言
     *
     * @param sqlDialect
     */
    public void setSqlDialect(SQLDialect sqlDialect) {
        this.sqlDialect = sqlDialect;
    }

    /**
     * 开始构建建表SQL
     *
     * @param tableSchema
     * @return
     */
    public String createTableSql(TableSchema tableSchema) {
//        构建表名
        String tableName = sqlDialect.wrapTableName(tableSchema.getTableName());
        String dbName = tableSchema.getDbName();
        if (StrUtil.isNotBlank(dbName))
            tableName = String.format("%s.%s", dbName, tableName);
//        构造表前缀注释
        String tableNote = tableSchema.getTableNote();
        if (StrUtil.isBlank(tableNote))
            tableNote = tableName;
        String tablePrefixNote = String.format("-- %s", tableNote);
//        构建表后缀注释
        String tableSuffixNote = String.format("comment '%s'", tableNote);
//        构造表字段
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
        StringBuilder stringBuilder = new StringBuilder();
        int size = fieldList.size();
        for (int i = 0; i < size; i++) {
            TableSchema.Field field = fieldList.get(i);
//            添加表SQL
            stringBuilder.append(buildCreateFieldSQL(field));
//            在每个字段后加","但最后一个字段不用
            if (i != size - 1) {
                stringBuilder.append(",");
                stringBuilder.append("\n");
            }
        }
//        最后生成的字段SQL
        String fieldStr = stringBuilder.toString();
        String resultSQL = String.format(Constants.create_table_template, tablePrefixNote, tableName, fieldStr, tableSuffixNote);
        log.info("生成的sql为==>" + resultSQL);
        return resultSQL;
    }

    /**
     * 生成创建字段的SQL
     * @param field
     * @return
     */
    public String buildCreateFieldSQL(TableSchema.Field field) {
        if(field == null)
            throw new CustomException(ErrorCode.PARAMS_ERROR);
        String fieldName = sqlDialect.wrapFieldName(field.getFieldName());
        String fieldType = field.getFieldType();
        String defaultValue = field.getDefaultValue();
        String note = field.getNote();
        String other = field.getOther();
        boolean notNull = field.isNotNull();
        boolean primaryKey = field.isPrimaryKey();
        boolean autoIncrement = field.isAutoIncrement();
//        开始构建
        StringBuilder stringBuilder = new StringBuilder();
//        字段名
        stringBuilder.append(fieldName);
//        字段类型
        stringBuilder.append(" ").append(fieldType);
//        添加默认值
        if(StrUtil.isNotBlank(defaultValue))
            stringBuilder.append(" ").append("default ").append(getValueStr(field,defaultValue));
//        是否非空
        stringBuilder.append(" ").append(notNull?"not null":"null");
//        是否自增
        if(autoIncrement)
            stringBuilder.append(" ").append("auto_increment");
//        附加条件
        if(StrUtil.isNotBlank(other))
            stringBuilder.append(" ").append("on update ").append(other);
//        注释
        if(StrUtil.isNotBlank(note))
            stringBuilder.append(" ").append(String.format("comment '%s'",note));
//        是否为主键
        if(autoIncrement)
            stringBuilder.append(" ").append("primary key");
        return stringBuilder.toString();
    }

    /**
     * 生成插入的SQL语句
     * @param tableSchema
     * @param dataList
     * @return
     */
    public String buildInsertSQL(TableSchema tableSchema, List<Map<String, Object>> dataList) {
//        插入模板
        String template = "insert into %s (%s) values (%s);";
//        构造表名
        String tableName = sqlDialect.wrapTableName(tableSchema.getTableName());
        String dbName = tableSchema.getDbName();
        if(StrUtil.isNotBlank(dbName))
            tableName = String.format("%s.%s",dbName,tableName);
//        构造表字段
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
//        过滤掉不模拟的字段
        fieldList = fieldList.stream().filter(field -> {
            MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType())).orElse(MockTypeEnum.NONE);
            return !MockTypeEnum.NONE.equals(mockTypeEnum);
        }).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < dataList.size(); i++) {
//            获取每一条要插入的数据
            Map<String, Object> dataRaw = dataList.get(i);
            String keyStr = fieldList.stream().map(field -> {
                return sqlDialect.wrapTableName(field.getFieldName());
            }).collect(Collectors.joining(", "));
            String valueStr = fieldList.stream().map(field -> {
                return getValueStr(field, dataRaw.get(field.getFieldName()));
            }).collect(Collectors.joining(", "));
//            填充模板
            String result = String.format(template, tableName, keyStr, valueStr);
            stringBuilder.append(result);
            if(dataList.size() -1 != i)
                stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    /**
     * 根据列的属性获取字符串
     * @param field
     * @param value
     * @return
     */
    private static String getValueStr(TableSchema.Field field, Object value) {
        if(field == null || value == null)
            return "''";
        FieldTypeEnum fieldTypeEnum = Optional
                .ofNullable(FieldTypeEnum.getEnumByValue(field.getFieldType()))
                .orElse(FieldTypeEnum.TEXT);
        switch (fieldTypeEnum){
            case DATE:
            case TIME:
            case DATETIME:
            case CHAR:
            case VARCHAR:
            case TINYTEXT:
            case TEXT:
            case MEDIUMTEXT:
            case LONGTEXT:
            case TINYBLOB:
            case BLOB:
            case MEDIUMBLOB:
            case LONGBLOB:
            case BINARY:
            case VARBINARY:
                return String.format("'%s'", value);
            default:
                return String.valueOf(value);
        }
    }


}

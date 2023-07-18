package org.example.core;

/**
 * Author: ZhaoXing

 */

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.example.core.build.DataBuilder;
import org.example.core.build.JsonBuilder;
import org.example.core.build.SQLBuilder;
import org.example.core.constant.Constants;
import org.example.core.model.vo.GeneratorVo;
import org.example.core.schema.SchemaException;
import org.example.core.schema.TableSchema;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 集中数据生成器
 * 门面模式 统一生成
 */
@Component
public class GeneratorFacade {

    public static GeneratorVo generateAll(TableSchema tableSchema) {
//        1.校验
        validSchema(tableSchema);
//        2.构造sql
        SQLBuilder sqlBuilder = new SQLBuilder();
        String tableSql = sqlBuilder.createTableSql(tableSchema);
//        3.生成模拟数据
//        3.1 获取数据条数
        Integer mockNum = tableSchema.getMockNum();
//        生成假数据
        List<Map<String, Object>> dataList = DataBuilder.generateData(tableSchema, mockNum);
//        生成插入的SQL语句
        String insertSQL = sqlBuilder.buildInsertSQL(tableSchema, dataList);
//        生成json语句
        String dataJson = JsonBuilder.buildJson(dataList);
//        JavaCodeBuilder.buildJavaEntityCode(tableSchema);
        GeneratorVo generatorVo = new GeneratorVo();
        generatorVo.setCreateSql(tableSql);
        generatorVo.setDataJson(dataJson);
        generatorVo.setInsertSql(insertSQL);
        generatorVo.setDataList(dataList);
        return generatorVo;
    }

    /**
     * 校验 schema
     *
     * @param tableSchema
     */
    private static void validSchema(TableSchema tableSchema) {
        if (tableSchema == null)
            throw new SchemaException("数据为空");
        if (StrUtil.isBlank(tableSchema.getTableName()))
            throw new SchemaException("表面不能为空");
        Integer mockNum = tableSchema.getMockNum();
        if (mockNum == null) {
            tableSchema.setMockNum(Constants.SCHEMA_MOCKNUM);
            mockNum = Constants.SCHEMA_MOCKNUM;
        }
        if (mockNum > 100 || mockNum < 10)
            throw new SchemaException("生成的条数设置错误");
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
        if (CollectionUtil.isEmpty(fieldList))
            throw new SchemaException("字段列表不能为空");

        for (TableSchema.Field field : fieldList) {
            validField(field);
        }
    }

    /**
     * 校验字段
     *
     * @param field
     */
    private static void validField(TableSchema.Field field) {
        String fieldName = field.getFieldName();
        String fieldType = field.getFieldType();
        if (StrUtil.isBlank(fieldName))
            throw new SchemaException("字段名不能为空");
        if (StrUtil.isBlank(fieldType))
            throw new SchemaException("字段类型不能为空");
    }
}

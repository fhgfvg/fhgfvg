package org.example.core.build;

/**
 * Author: ZhaoXing

 */

import cn.hutool.core.collection.CollectionUtil;
import org.example.core.generator.DataGenerator;
import org.example.core.generator.DataGeneratorFactory;
import org.example.core.model.enums.MockTypeEnum;
import org.example.core.schema.TableSchema;

import java.util.*;

/**
 * 数据生成器
 */
public class DataBuilder {
    /**
     * 生成假数据
     *
     * @param tableSchema 表概要
     * @param num         生成的条数
     * @return
     */
    public static List<Map<String, Object>> generateData(TableSchema tableSchema, int num) {
//        获取字段列表
        List<TableSchema.Field> fieldList = tableSchema.getFieldList();
//        初始化结果数据容器
        List<Map<String, Object>> dataList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            dataList.add(new HashMap<>());
        }
//        开始生成每一列数据
        for (TableSchema.Field field : fieldList) {
//            如果不为空则返回传入的值,否则返回orElse中的值.这可以很好地解决空指针异常.
            MockTypeEnum mockTypeEnum = Optional.ofNullable(MockTypeEnum.getEnumByValue(field.getMockType()))
                    .orElse(MockTypeEnum.NONE);
//            获取数据生成器实例
            DataGenerator dataGenerator = DataGeneratorFactory.getDataGenerator(mockTypeEnum);
//            获取假数据
            List<String> mockDataList = dataGenerator.doGenerator(field, num);
            String fieldName = field.getFieldName();
//            循环遍历,放入dataList中
            if (CollectionUtil.isNotEmpty(mockDataList)) {
                for (int i = 0; i < num; i++) {
                    dataList.get(i).put(fieldName, mockDataList.get(i));
                }
            }
        }
        return dataList;
    }
}

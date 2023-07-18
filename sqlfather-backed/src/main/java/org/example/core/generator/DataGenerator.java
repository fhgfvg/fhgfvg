package org.example.core.generator;

/**
 * Author: ZhaoXing

 */

import org.example.core.schema.TableSchema;

import java.util.List;

/**
 * 数据生成器泛型接口
 */
public interface DataGenerator {

    /**
     * 生成数据
     * @param field 列字段信息
     * @param num 生成的数据长度
     * @return
     */
    List<String> doGenerator(TableSchema.Field field,int num);
}

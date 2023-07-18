package org.example.core.generator;

/**
 * Author: ZhaoXing

 */

import cn.hutool.core.util.StrUtil;
import org.example.core.schema.TableSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * 递增数据生成器
 */
public class IncrementDataGenerator implements DataGenerator {
    @Override
    public List<String> doGenerator(TableSchema.Field field, int num) {
        String mockParam = field.getMockParam();
        ArrayList<String> list = new ArrayList<>(num);
        if (StrUtil.isBlank(mockParam))
            mockParam = "1";
        int initValue = Integer.parseInt(mockParam);
        for (int i = 0; i < num; i++) {
            list.add(String.valueOf(initValue + i));
        }
        return list;
    }
}

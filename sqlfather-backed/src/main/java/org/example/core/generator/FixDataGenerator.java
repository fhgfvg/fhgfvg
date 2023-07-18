package org.example.core.generator;

/**
 * Author: ZhaoXing

 */

import cn.hutool.core.util.StrUtil;
import org.example.core.schema.TableSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * 固定数据生成器
 */
public class FixDataGenerator implements DataGenerator{

    @Override
    public List<String> doGenerator(TableSchema.Field field, int num) {
        String mockParam = field.getMockParam();
        if(StrUtil.isBlank(mockParam))
//            给默认值
            mockParam = "6";
        ArrayList<String> list = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            list.add(mockParam);
        }
        return list;
    }
}

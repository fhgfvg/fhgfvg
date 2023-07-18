package org.example.core.generator;

import com.mifmif.common.regex.Generex;
import org.example.core.schema.TableSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: ZhaoXing

 */

/**
 * 正则表达式数据生成器
 */
public class FormatDataGenerator implements DataGenerator {
    @Override
    public List<String> doGenerator(TableSchema.Field field, int num) {
        String mockParam = field.getMockParam();
        ArrayList<String> list = new ArrayList<>(num);
//        根据正则生成随机字符串
        Generex generex = new Generex(mockParam);
        for (int i = 0; i < num; i++) {
            String randomValue = generex.random();
            list.add(randomValue);
        }
        return list;
    }
}

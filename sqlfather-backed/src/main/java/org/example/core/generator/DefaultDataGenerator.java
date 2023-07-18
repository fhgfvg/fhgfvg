package org.example.core.generator;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.example.core.schema.TableSchema;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: ZhaoXing

 */

/**
 * 默认数据生成器
 */
public class DefaultDataGenerator implements DataGenerator {


    @Override
    public List<String> doGenerator(TableSchema.Field field, int num) {
        String mockParam = field.getMockParam();
        ArrayList<String> list = new ArrayList<>(num);
//        如果该字段为主键字段,则自增
        if (field.isPrimaryKey()) {
            if (StrUtil.isBlank(mockParam)) {
                mockParam = "1";
            }
            int initValue = Integer.parseInt(mockParam);
            for (int i = 0; i < num; i++) {
                list.add(String.valueOf(initValue + i));
            }
            return list;
        }
//        使用默认值
        String defaultValue = field.getDefaultValue();
//        时间格式要额外处理
        if (defaultValue == "CURRENT_TIMESTAMP") {
            defaultValue = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        if (StrUtil.isNotBlank(defaultValue)) {
            for (int i = 0; i < num; i++) {
                list.add(defaultValue);
            }
        }
        return list;
    }
}

package org.example.core.generator;

/**
 * Author: ZhaoXing
 */

import cn.hutool.core.util.StrUtil;
import org.example.core.model.enums.MockParamsRandomTypeEnum;
import org.example.core.schema.TableSchema;
import org.example.core.utils.FakerUtils;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 随机值数据生成器
 */
public class RandomDataGenerator implements DataGenerator {
    @Override
    public List<String> doGenerator(TableSchema.Field field, int num) {
        String mockParam = field.getMockParam();
        ArrayList<String> list = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            MockParamsRandomTypeEnum mockParamsRandomTypeEnum = Optional
                    .ofNullable(MockParamsRandomTypeEnum.getEnumByValue(mockParam))
                    .orElse(MockParamsRandomTypeEnum.STRING);
            String randomValue = FakerUtils.getRandomValue(mockParamsRandomTypeEnum);
            list.add(randomValue);
        }
        return list;
    }
}

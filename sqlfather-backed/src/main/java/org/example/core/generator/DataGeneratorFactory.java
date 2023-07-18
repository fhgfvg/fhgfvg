package org.example.core.generator;

/**
 * Author: ZhaoXing

 */

import org.example.core.model.enums.MockTypeEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 数据生成器工厂
 * 工厂+单例模式
 */
public class DataGeneratorFactory {
    private static final Map<MockTypeEnum,DataGenerator> map = new HashMap<MockTypeEnum,DataGenerator>(){
        {
            put(MockTypeEnum.NONE,new DefaultDataGenerator());
            put(MockTypeEnum.FIXED,new FixDataGenerator());
            put(MockTypeEnum.RANDOM,new RandomDataGenerator());
            put(MockTypeEnum.INCREASE,new IncrementDataGenerator());
            put(MockTypeEnum.RULE,new FormatDataGenerator());
            put(MockTypeEnum.DICT,new DictDataGenerator());
        }
    };

    /**
     * 获取生成器实例
     * @param mockTypeEnum
     * @return
     */
    public static DataGenerator getDataGenerator(MockTypeEnum mockTypeEnum){
        mockTypeEnum = Optional.ofNullable(mockTypeEnum).orElse(MockTypeEnum.NONE);
        return map.get(mockTypeEnum);
    }

}

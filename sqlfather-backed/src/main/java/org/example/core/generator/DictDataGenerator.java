package org.example.core.generator;

import org.example.core.schema.TableSchema;

import java.util.List;

/**
 * Author: ZhaoXing

 */
public class DictDataGenerator implements DataGenerator{

    //todo
    @Override
    public List<String> doGenerator(TableSchema.Field field, int num) {
        String mockParam = field.getMockParam();
        long id = Long.parseLong(mockParam);
        return null;
    }
}

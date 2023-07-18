package org.example.core.build;

/**
 * Author: ZhaoXing

 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

/**
 * 数据Json生成器
 */
public class JsonBuilder {

    /**
     * 转Json数据
     * @param dataList 数据列表
     * @return
     */
    public static String buildJson(List<Map<String,Object>> dataList){
//        使用Gson包转Json
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return gson.toJson(dataList);
    }
}

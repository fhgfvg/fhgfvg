package org.example.core.build;

/**
 * Author: ZhaoXing

 */

import freemarker.template.Configuration;
import org.example.core.schema.TableSchema;

import javax.annotation.Resource;
import javax.annotation.Resources;

/**
 * Java 代码生成器
 */
public class JavaCodeBuilder {
    private static Configuration configuration;

    @Resource
    public void setConfiguration(Configuration configuration){
        JavaCodeBuilder.configuration = configuration;
    }
//todo
//    public static String buildJavaEntityCode(TableSchema tableSchema){
//
//    }

}

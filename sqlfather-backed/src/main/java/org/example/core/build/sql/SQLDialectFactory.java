package org.example.core.build.sql;

/**
 * Author: ZhaoXing

 */

import org.example.core.schema.SchemaException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SQL 方言工厂
 * 工厂+单例模式
 */
public class SQLDialectFactory {
    private static final Map<String, SQLDialect> DIALECT_POOL = new ConcurrentHashMap<>();

    /**
     * 获取方言实例
     *
     * @param className 类名
     * @return
     */
    public static SQLDialect getDialect(String className) {
//        从map中获取SQLDialect实例对象
        SQLDialect sqlDialect = DIALECT_POOL.get(className);
//        第一次获取时为空
        if (sqlDialect == null) {
            synchronized (className.intern()) {
//                创建该实例并放入map中
                sqlDialect = DIALECT_POOL.computeIfAbsent(className, (key) -> {
                    System.out.println("sql方言工厂中key=" + key);
                    try {
                        return (SQLDialect) Class.forName(className).newInstance();
                    } catch (Exception e) {
                        throw new SchemaException(e);
                    }
                });
            }
        }
        return sqlDialect;
    }
}

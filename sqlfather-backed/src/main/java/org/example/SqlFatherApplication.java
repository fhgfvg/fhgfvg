package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Author: ZhaoXing
 */
@SpringBootApplication
@MapperScan("org.example.mapper")
public class SqlFatherApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlFatherApplication.class,args);
    }
}

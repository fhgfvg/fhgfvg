package org.example.core.schema;

/**
 * Author: ZhaoXing

 */


/**
 * sql 相关异常
 */
public class SchemaException extends RuntimeException{

    public SchemaException(String msg){
        super(msg);
    }
    public SchemaException(String msg,Throwable t){
        super(msg,t);
    }
    public SchemaException(Throwable t){
        super(t);
    }
}

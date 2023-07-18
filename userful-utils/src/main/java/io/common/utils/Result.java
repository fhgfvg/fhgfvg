package io.common.utils;

/**
 * Author: ZhaoXing

 */

import io.common.enums.ErrorCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一的返回类
 */
@Data
@NoArgsConstructor
public class Result<T> {
    /**
     * 返回消息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 状态码
     */
    private int code;

    /**
     * 成功信息
     */
    public Result<T> ok(String msg) {
        return ok(msg, null);
    }

    public Result<T> ok(T data) {
        return ok("success", data);
    }

    public Result<T> ok(String msg, T data) {
        this.msg = msg;
        setData(data);
        return this;
    }

    /**
     * 错误返回
     */
    public Result<T> error(int code) {
        return error(code, "error");
    }

    public Result<T> error(int code, String msg) {
        this.msg = msg;
        this.code = code;
        return this;
    }
    public Result<T> error(ErrorCode errorCode) {
        this.msg = errorCode.getMsg();
        this.code = errorCode.getCode();
        return this;
    }
    public Result<T> error(ErrorCode errorCode,String msg) {
        this.msg = msg;
        this.code = errorCode.getCode();
        return this;
    }

}

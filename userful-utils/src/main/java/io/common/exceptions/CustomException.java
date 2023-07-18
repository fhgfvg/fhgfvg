package io.common.exceptions;

/**
 * Author: ZhaoXing

 */

import io.common.enums.ErrorCode;

/**
 * 自定义异常类
 */
public class CustomException extends RuntimeException {
    private final int code;

    public CustomException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMsg());
        this.code = errorCode.getCode();
    }

    public CustomException(ErrorCode errorCode, String msg) {
        super(msg);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}

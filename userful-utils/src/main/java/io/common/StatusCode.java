package io.common;

/**
 * Author: ZhaoXing

 */

/*状态码*/
public interface StatusCode {

    /**
     * 内部服务错误
     */
    int INTERNAL_SERVER_ERROR = 500;

    /**
     * 未授权
     */
    int UNAUTHORIZED = 401;
}
